import pandas as pd
import numpy as np
import torch
import torch.nn as nn
import pickle
import sys
import json
import warnings
from pathlib import Path

# ---------------------------------------------------------
# 1. 환경 설정
# ---------------------------------------------------------
warnings.filterwarnings("ignore")
if sys.stdout.encoding.lower() != 'utf-8':
    sys.stdout.reconfigure(encoding='utf-8')

BASE_DIR = Path(__file__).resolve().parent.parent
MODEL_PATH = BASE_DIR / "uploads" / "index" / "deep_senior.pth"
META_PATH = BASE_DIR / "uploads" / "index" / "deep_meta.pkl"

# ---------------------------------------------------------
# 2. 딥러닝 모델 정의
# ---------------------------------------------------------
class DeepFashionRecommender(nn.Module):
    def __init__(self, vocab_sizes, num_numeric_features, embed_dim=16):
        super(DeepFashionRecommender, self).__init__()
        self.gender_embedding = nn.Embedding(3, embed_dim)
        self.prod_gender_embedding = nn.Embedding(3, embed_dim)
        self.hobby_embedding = nn.EmbeddingBag(vocab_sizes['hobby'], embed_dim, mode='mean')
        self.color_embedding = nn.EmbeddingBag(vocab_sizes['color'], embed_dim, mode='mean')
        self.season_embedding = nn.Embedding(vocab_sizes['season'], embed_dim)
        self.fit_embedding = nn.Embedding(vocab_sizes['fit'], embed_dim)
        self.pattern_embedding = nn.Embedding(vocab_sizes['pattern'], embed_dim)
        self.thickness_embedding = nn.Embedding(vocab_sizes['thickness'], embed_dim)
        
        self.numeric_layer = nn.Sequential(nn.Linear(num_numeric_features, 16), nn.ReLU())
        deep_input_dim = (embed_dim * 8) + 16
        
        self.deep_layers = nn.Sequential(
            nn.Linear(deep_input_dim, 512), nn.ReLU(), nn.BatchNorm1d(512), nn.Dropout(0.4),
            nn.Linear(512, 256), nn.ReLU(), nn.BatchNorm1d(256), nn.Dropout(0.3),
            nn.Linear(256, 64), nn.ReLU(), nn.Linear(64, 1)
        )

    def forward(self, num_x, gen_x, pgen_x, sea_x, hob_x, hob_off, col_x, col_off, fit_x, pat_x, thi_x):
        emb_gen = self.gender_embedding(gen_x)
        emb_pgen = self.prod_gender_embedding(pgen_x)
        emb_sea = self.season_embedding(sea_x)
        emb_hob = self.hobby_embedding(hob_x, hob_off)
        emb_col = self.color_embedding(col_x, col_off)
        emb_fit = self.fit_embedding(fit_x)
        emb_pat = self.pattern_embedding(pat_x)
        emb_thi = self.thickness_embedding(thi_x)
        num_out = self.numeric_layer(num_x)
        concat = torch.cat([emb_gen, emb_pgen, emb_sea, emb_hob, emb_col, emb_fit, emb_pat, emb_thi, num_out], dim=1)
        return self.deep_layers(concat)

# ---------------------------------------------------------
# 3. 추천 엔진 클래스
# ---------------------------------------------------------
class RecommenderEngine:
    def __init__(self, model_p=MODEL_PATH, meta_p=META_PATH):
        self.device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
        with open(meta_p, 'rb') as f:
            meta = pickle.load(f)
            self.encoders = meta['encoders']
            self.scaler = meta['scaler']
            self.hobby_vocab = meta['hobby_vocab']
            self.color_vocab = meta['color_vocab']
            
        vocab_sizes = {
            'season': len(self.encoders['season']),
            'hobby': len(self.hobby_vocab),
            'color': len(self.color_vocab),
            'fit': len(self.encoders['productFit']),
            'pattern': len(self.encoders['productPattern']),
            'thickness': len(self.encoders['productThickness'])
        }
        self.model = DeepFashionRecommender(vocab_sizes, 4).to(self.device)
        self.model.load_state_dict(torch.load(model_p, map_location=self.device, weights_only=True))
        self.model.eval()

    def _encode_list(self, data_str, vocab):
        if not data_str: return torch.tensor([vocab["<UNK>"]], dtype=torch.long)
        words = [w.strip() for w in str(data_str).split(',')]
        encoded = [vocab.get(w, vocab["<UNK>"]) for w in words]
        return torch.tensor(encoded, dtype=torch.long)

    def get_recommendations(self, user_data, product_list, current_season, top_k=10):
        if not product_list: return []
        
        age = float(user_data.get('age', 0))
        h = float(user_data.get('height', 0))
        w = float(user_data.get('weight', 0))
        bmi = w / ((h / 100) ** 2) if h > 0 else 22.0
        
        num_scaled = self.scaler.transform([[age, h, w, bmi]])
        n_x = torch.tensor(num_scaled, dtype=torch.float32).to(self.device)
        user_gender = int(user_data.get('gender', 0))
        gen_x = torch.tensor([user_gender], dtype=torch.long).to(self.device)
        
        hob_x = self._encode_list(user_data.get('hobbies', ""), self.hobby_vocab).to(self.device)
        hob_off = torch.tensor([0], dtype=torch.long).to(self.device)
        col_x = self._encode_list(user_data.get('pref_colors', ""), self.color_vocab).to(self.device)
        col_off = torch.tensor([0], dtype=torch.long).to(self.device)
        
        results = []
        with torch.no_grad():
            for product in product_list:
                p_id = int(product.get('productNo', 0))
                p_gender = int(product.get('productGender', 0))
                
                try:
                    def get_idx(col, val): return self.encoders[col].get(str(val).strip(), 0)
                    pgen_t = torch.tensor([p_gender], dtype=torch.long).to(self.device)
                    s_t = torch.tensor([get_idx('season', product.get('season', ''))], dtype=torch.long).to(self.device)
                    f_t = torch.tensor([get_idx('productFit', product.get('productFit', ''))], dtype=torch.long).to(self.device)
                    pat_t = torch.tensor([get_idx('productPattern', product.get('productPattern', ''))], dtype=torch.long).to(self.device)
                    th_t = torch.tensor([get_idx('productThickness', product.get('productThickness', ''))], dtype=torch.long).to(self.device)
                    
                    score = self.model(n_x, gen_x, pgen_t, s_t, hob_x, hob_off, col_x, col_off, f_t, pat_t, th_t).item()
                    results.append({"productNo": p_id, "score": score, "raw": product})
                except: continue
                
        results.sort(key=lambda x: x['score'], reverse=True)
        return results[:top_k]

# ---------------------------------------------------------
# 4. 실행 메인 (배열 순회 방식 적용)
# ---------------------------------------------------------
def main():
    if len(sys.argv) < 2: return
    try:
        input_file = sys.argv[1]
        with open(input_file, 'r', encoding='utf-8') as f:
            raw_json = json.load(f)
            
        engine = RecommenderEngine() 
        active_products = raw_json.get('active_products', [])
        current_season = raw_json.get('current_season', '봄')
        me = raw_json.get('me', {})
        family = raw_json.get('family')
        
        my_hobbies = [h.strip() for h in me.get('hobbies', '').split(',') if h.strip()]
        my_colors = [c.strip() for c in me.get('pref_colors', '').split(',') if c.strip()]
        
        used_ids = set()
        final_output = []
        
        def get_filtered_products(target_gender_val):
            valid = []
            for p in active_products:
                if p['productNo'] in used_ids: continue
                p_gender = int(p.get('productGender', 0))
                if target_gender_val == 2 and p_gender == 2: continue
                if target_gender_val == 1 and p_gender == 1: continue
                valid.append(p)
            return valid

        # --- 추천 슬롯 분배 ---
        buckets = ['physical', 'season'] 
        if my_colors: buckets.append('color') 
        if my_hobbies: buckets.append('hobby')
        if family: buckets.append('family')
        
        allocations = {b: 10 // len(buckets) for b in buckets}
        for i in range(10 % len(buckets)): allocations[buckets[i]] += 1
        
        # [버킷 1] 체형
        if allocations['physical'] > 0:
            avail = get_filtered_products(int(me.get('gender', 0)))
            recs = engine.get_recommendations(me, avail, current_season, top_k=allocations['physical'])
            for r in recs:
                used_ids.add(r['productNo'])
                final_output.append({"productNo": r['productNo'], "phrase": "🧸 체형에 맞춰 편하게 입기 좋아 보여서 골라봤어요"})

        # [버킷 2] 날씨
        if allocations['season'] > 0:
            avail = get_filtered_products(int(me.get('gender', 0)))
            recs = engine.get_recommendations(me, avail, current_season, top_k=allocations['season'])
            
            if '봄' in current_season: season_phrase = "🌸 화창한 봄 날씨에 잘 어울리는 옷이에요"
            elif '여름' in current_season: season_phrase = "☀️ 무더운 여름에 시원하게 입기 좋아요"
            elif '가을' in current_season: season_phrase = "🍂 선선한 가을 외출에 어울리는 옷이에요"
            elif '겨울' in current_season: season_phrase = "❄️ 쌀쌀한 겨울에 따뜻하게 입기 좋아요"
            else: season_phrase = "☀️ 오늘 날씨에 잘 어울리는 옷이에요"

            for r in recs:
                used_ids.add(r['productNo'])
                final_output.append({"productNo": r['productNo'], "phrase": season_phrase})

        # [버킷 3] 선호 색상 (순서대로 골고루 적용)
        if allocations.get('color', 0) > 0:
            avail = get_filtered_products(int(me.get('gender', 0)))
            recs = engine.get_recommendations(me, avail, current_season, top_k=allocations['color'])
            
            for idx, r in enumerate(recs):
                used_ids.add(r['productNo'])
                if my_colors:
                    # 고객이 선택한 색상을 하나씩 번갈아가며 가져옴 (예: 1번째 상품은 연갈색, 2번째 상품은 자주색...)
                    selected_color = my_colors[idx % len(my_colors)]
                    color_phrase = f"🎨 평소 좋아하시는 '{selected_color}' 느낌으로 준비해봤어요"
                else:
                    color_phrase = "✨ 요즘 자주 찾으시는 느낌이라 준비해봤어요"
                    
                final_output.append({"productNo": r['productNo'], "phrase": color_phrase})

        # [버킷 4] 취미 (순서대로 골고루 적용 + 이모지도 다르게!)
        if allocations.get('hobby', 0) > 0:
            avail = get_filtered_products(int(me.get('gender', 0)))
            recs = engine.get_recommendations(me, avail, current_season, top_k=allocations['hobby'])
            
            for idx, r in enumerate(recs):
                used_ids.add(r['productNo'])
                
                # 고객이 선택한 취미를 하나씩 번갈아가며 가져옴
                selected_hobby = my_hobbies[idx % len(my_hobbies)] if my_hobbies else "외출"
                
                emoji = "✨"
                if '낚시' in selected_hobby: emoji = "🎣"
                elif '등산' in selected_hobby: emoji = "⛰️"
                elif '자전거' in selected_hobby: emoji = "🚴"
                elif '요가' in selected_hobby or '수영' in selected_hobby or '운동' in selected_hobby: emoji = "🏃"
                elif '텃밭' in selected_hobby: emoji = "🌱"
                elif '외출' in selected_hobby or '산책' in selected_hobby: emoji = "🚶"
                
                hobby_phrase = f"{emoji} 즐겨하시는 '{selected_hobby}' 활동에 딱 어울리는 스타일이에요"
                
                final_output.append({"productNo": r['productNo'], "phrase": hobby_phrase})

        # [버킷 5] 가족 선물
        if allocations.get('family', 0) > 0 and family:
            fam_gender = int(family.get('gender', 0))
            f_name = family.get('userName', '가족')
            
            avail = get_filtered_products(fam_gender)
            recs = engine.get_recommendations(family, avail, current_season, top_k=allocations['family'])
            
            for r in recs:
                used_ids.add(r['productNo'])
                final_output.append({
                    "productNo": r['productNo'],
                    "phrase": f"🎁 오늘 {f_name}님께 선물해보는 건 어떠세요?",
                    "target": f_name
                })

        # 최종 JSON 출력
        print(json.dumps(final_output, ensure_ascii=False))
        
    except Exception as e:
        print(json.dumps([{"productNo":0, "phrase": str(e)}], ensure_ascii=False))

if __name__ == "__main__":
    main()