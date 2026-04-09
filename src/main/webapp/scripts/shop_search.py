import pandas as pd
import numpy as np
import torch
import torch.nn as nn
import pickle
import os
import json
import sys
from pathlib import Path

# ---------------------------------------------------------
# 1. 경로 설정 (shop_search.py와 동일한 방식)
# ---------------------------------------------------------
BASE_DIR = Path(__file__).resolve().parent
# 추천용 모델과 메타데이터도 같은 index 폴더 내에 위치하도록 설정
MODEL_PATH = (BASE_DIR / "../index/deep_senior.pth").resolve()
META_PATH = (BASE_DIR / "../index/deep_meta.pkl").resolve()

# ---------------------------------------------------------
# 2. 모델 구조 정의
# ---------------------------------------------------------
class DeepFashionRecommender(nn.Module):
    def __init__(self, vocab_sizes, num_numeric_features, embed_dim=16):
        super(DeepFashionRecommender, self).__init__()
        self.gender_embedding = nn.Embedding(3, embed_dim)
        self.hobby_embedding = nn.EmbeddingBag(vocab_sizes['hobby'], embed_dim, mode='mean')
        self.season_embedding = nn.Embedding(vocab_sizes['season'], embed_dim)
        self.fit_embedding = nn.Embedding(vocab_sizes['fit'], embed_dim)
        self.pattern_embedding = nn.Embedding(vocab_sizes['pattern'], embed_dim)
        self.thickness_embedding = nn.Embedding(vocab_sizes['thickness'], embed_dim)
        
        self.numeric_layer = nn.Sequential(nn.Linear(num_numeric_features, 16), nn.ReLU())
        
        deep_input_dim = (embed_dim * 6) + 16
        self.deep_layers = nn.Sequential(
            nn.Linear(deep_input_dim, 512), nn.ReLU(), nn.BatchNorm1d(512), nn.Dropout(0.4),
            nn.Linear(512, 256), nn.ReLU(), nn.BatchNorm1d(256), nn.Dropout(0.3),
            nn.Linear(256, 64), nn.ReLU(), nn.Linear(64, 1)
        )

    def forward(self, numeric_x, gender_x, season_x, hobby_x, hobby_offsets, fit_x, pat_x, thi_x):
        emb_gender = self.gender_embedding(gender_x)
        emb_season = self.season_embedding(season_x)
        emb_hobby = self.hobby_embedding(hobby_x, hobby_offsets)
        emb_fit = self.fit_embedding(fit_x)
        emb_pat = self.pattern_embedding(pat_x)
        emb_thi = self.thickness_embedding(thi_x)
        num_out = self.numeric_layer(numeric_x)
        concat = torch.cat([emb_gender, emb_season, emb_hobby, emb_fit, emb_pat, emb_thi, num_out], dim=1)
        return self.deep_layers(concat)

# ---------------------------------------------------------
# 3. 추천 엔진 클래스
# ---------------------------------------------------------
class RecommenderEngine:
    def __init__(self):
        self.device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
        
        # 통합 index 폴더에서 메타데이터 로드
        if not META_PATH.exists():
            raise FileNotFoundError(f"메타데이터 파일을 찾을 수 없습니다: {META_PATH}")
            
        with open(META_PATH, 'rb') as f:
            meta = pickle.load(f)
            self.encoders = meta['encoders']
            self.scaler = meta['scaler']
            self.hobby_vocab = meta['hobby_vocab']
            self.product_db = meta['db']

        vocab_sizes = {
            'season': len(self.encoders['season']),
            'hobby': len(self.hobby_vocab),
            'fit': len(self.encoders['productFit']),
            'pattern': len(self.encoders['productPattern']),
            'thickness': len(self.encoders['productThickness'])
        }
        
        # 모델 로드
        self.model = DeepFashionRecommender(vocab_sizes, 4).to(self.device)
        if not MODEL_PATH.exists():
            raise FileNotFoundError(f"모델 파일을 찾을 수 없습니다: {MODEL_PATH}")
            
        self.model.load_state_dict(torch.load(MODEL_PATH, map_location=self.device, weights_only=True))
        self.model.eval()

    def _encode_hobbies(self, hobby_str):
        # 자바에서 "축구,등산" 처럼 쉼표로 올 경우 대비
        hobbies = hobby_str.split(',') if hobby_str else []
        if not hobbies: return torch.tensor([self.hobby_vocab["<UNK>"]], dtype=torch.long)
        encoded = [self.hobby_vocab.get(w.strip(), self.hobby_vocab["<UNK>"]) for w in hobbies]
        return torch.tensor(encoded, dtype=torch.long)

    def get_recommendations(self, user_data, top_k=10):
        # 데이터 전처리
        age = float(user_data.get('age', 0))
        h = float(user_data.get('height', 170))
        w = float(user_data.get('weight', 60))
        bmi = w / ((h / 100) ** 2) if h > 0 else 0
        
        num_scaled = self.scaler.transform([[age, h, w, bmi]])
        
        n_x = torch.tensor(num_scaled, dtype=torch.float32).to(self.device)
        g_x = torch.tensor([int(user_data.get('gender', 0))], dtype=torch.long).to(self.device)
        h_x = self._encode_hobbies(user_data.get('hobbies', "")).to(self.device)
        h_off = torch.tensor([0], dtype=torch.long).to(self.device)

        bought_ids = set(user_data.get('bought_ids', []))
        results = []

        with torch.no_grad():
            for _, product in self.product_db.iterrows():
                p_id = int(product['productNo'])
                if p_id in bought_ids: continue

                try:
                    def get_idx(col, val): return self.encoders[col].get(str(val).strip(), 0)
                    s_x = torch.tensor([get_idx('season', product['season'])], dtype=torch.long).to(self.device)
                    f_x = torch.tensor([get_idx('productFit', product['productFit'])], dtype=torch.long).to(self.device)
                    p_x = torch.tensor([get_idx('productPattern', product['productPattern'])], dtype=torch.long).to(self.device)
                    t_x = torch.tensor([get_idx('productThickness', product['productThickness'])], dtype=torch.long).to(self.device)
                    
                    pred = self.model(n_x, g_x, s_x, h_x, h_off, f_x, p_x, t_x).item()
                    results.append({"productNo": p_id, "score": pred})
                except: continue

        results.sort(key=lambda x: x['score'], reverse=True)
        return results[:top_k]

# ---------------------------------------------------------
# 4. 메인 실행부
# ---------------------------------------------------------
if __name__ == "__main__":
    if len(sys.argv) < 2:
        print(json.dumps({"error": "No input"}))
        sys.exit(1)

    try:
        # 자바에서 보낸 JSON 데이터 읽기 (파일 혹은 문자열)
        input_arg = sys.argv[1]
        if os.path.exists(input_arg):
            with open(input_arg, 'r', encoding='utf-8') as f:
                raw_json = json.load(f)
        else:
            raw_json = json.loads(input_arg)

        engine = RecommenderEngine()
        recommendations = engine.get_recommendations(raw_json['me'])

        final_output = []
        for rec in recommendations:
            final_output.append({
                "productNo": rec['productNo'],
                "phrase": f"당신의 신체 조건과 평소 스타일을 분석한 결과, 이 상품이 가장 잘 어울립니다."
            })

        print(json.dumps(final_output, ensure_ascii=False))

    except Exception as e:
        print(json.dumps({"error": str(e)}))