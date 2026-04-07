import os
import sys
import argparse
import faiss
import pickle
import torch
import numpy as np
import json
from PIL import Image
from pathlib import Path
from torchvision import transforms, models

# 경로 설정
BASE_DIR = Path(__file__).resolve().parent
IMG_DIR = (BASE_DIR / "../uploads/products").resolve()
INDEX_PATH = (BASE_DIR / "../index/shop_index.faiss").resolve()
META_PATH = (BASE_DIR / "../index/shop_meta.pkl").resolve()

# 모델 로드 (경고 방지를 위해 최신 방식으로 수정)
weights = models.ResNet50_Weights.DEFAULT
model = models.resnet50(weights=weights)
model.eval()
preprocess = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
])

def extract_features(img_path):
    img = Image.open(img_path).convert('RGB')
    input_tensor = preprocess(img).unsqueeze(0)
    with torch.no_grad():
        feature = model(input_tensor)
    return feature.numpy().flatten()

def build_active_index():
    """능동적 빌드: 폴더 스캔 후 인덱스 재생성"""
    if not IMG_DIR.exists(): return False
    os.makedirs(INDEX_PATH.parent, exist_ok=True)

    new_descriptions = []
    new_file_paths = []
    features_list = []

    valid_images = [f for f in IMG_DIR.glob("*.jpg") if "_main" in f.name]
    if not valid_images: return False

    for img_path in valid_images:
        try:
            product_id = int(img_path.name.split('_')[1])
            feat = extract_features(str(img_path))
            features_list.append(feat)
            new_descriptions.append(product_id)
            new_file_paths.append(img_path.name)
        except: continue

    if not features_list: return False

    features_np = np.vstack(features_list).astype('float32')
    index = faiss.IndexFlatL2(features_np.shape[1])
    index.add(features_np)

    faiss.write_index(index, str(INDEX_PATH))
    with open(META_PATH, 'wb') as f:
        pickle.dump({'descriptions': new_descriptions, 'file_paths': new_file_paths}, f)
    return True

def search_index(query_img_path, top_k=6):
    if not INDEX_PATH.exists(): return []
    index = faiss.read_index(str(INDEX_PATH))
    with open(META_PATH, 'rb') as f:
        meta = pickle.load(f)

    query_feat = extract_features(query_img_path).reshape(1, -1).astype('float32')
    distances, indices = index.search(query_feat, top_k)

    results = []
    for i, idx in enumerate(indices[0]):
        if idx < len(meta['descriptions']):
            results.append({
                "rank": i + 1,
                "product_id": str(meta['descriptions'][idx]),
                "score": float(distances[0][i])
            })
    return results

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    # 인자 정의 (자바에서 던지는 모든 인자를 수용할 수 있게 구성)
    parser.add_argument('--mode', type=str, required=True)
    parser.add_argument('--image', type=str)
    parser.add_argument('--product-id', type=str) # 추가됨
    parser.add_argument('--name', type=str)       # 추가됨
    parser.add_argument('--json', action='store_true')
    
    args = parser.parse_args()

    # 실행 로직
    if args.mode == 'build':
        res = build_active_index()
        if args.json: print(json.dumps({"success": res}))
    elif args.mode == 'search':
        res = search_index(args.image)
        if args.json: print(json.dumps(res))