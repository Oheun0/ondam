import os, sys, argparse, faiss, pickle, torch, json
import numpy as np
from PIL import Image
from pathlib import Path
from torchvision import transforms, models
import io

# 💡 [추가] 콘솔 출력 시 한글 깨짐 방지
sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding='utf-8')
sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding='utf-8')

# 경로 설정
BASE_DIR = Path(__file__).resolve().parent
IMG_DIR = (BASE_DIR / "../uploads/products").resolve()
INDEX_PATH = (BASE_DIR / "../uploads/index/shop_index.faiss").resolve()
META_PATH = (BASE_DIR / "../uploads/index/shop_meta.pkl").resolve()

os.makedirs(os.path.dirname(INDEX_PATH), exist_ok=True)

# 모델 로드
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

# 💡 [추가] C++ 엔진의 한글 경로 에러를 우회하여 파이썬이 직접 FAISS 파일 저장
def save_faiss_index(index, path):
    chunk = faiss.serialize_index(index)
    with open(path, "wb") as f:
        f.write(chunk)

# 💡 [추가] C++ 엔진의 한글 경로 에러를 우회하여 파이썬이 직접 FAISS 파일 읽기
def load_faiss_index(path):
    with open(path, "rb") as f:
        data = f.read()
    return faiss.deserialize_index(np.frombuffer(data, dtype=np.uint8))

def build_active_index(id_list_str, img_list_str):
    try:
        if not id_list_str or not img_list_str:
            return False
            
        new_descriptions, new_file_paths, features_list = [], [], []
        p_ids = [int(x) for x in id_list_str.split(',')]
        img_names = [x.strip() for x in img_list_str.split(',')]
        
        for p_id, img_name in zip(p_ids, img_names):
            img_path = (IMG_DIR / img_name).resolve()
            if img_path.exists():
                try:
                    feat = extract_features(str(img_path))
                    features_list.append(feat)
                    new_descriptions.append(p_id)
                    new_file_paths.append(img_name)
                except Exception as e:
                    pass
                    
        if not features_list:
            return False
            
        features_np = np.vstack(features_list).astype('float32')
        index = faiss.IndexFlatL2(features_np.shape[1])
        index.add(features_np)
        
        # 💡 [수정] 우회 저장 함수 사용
        save_faiss_index(index, str(INDEX_PATH))
        with open(META_PATH, 'wb') as f:
            pickle.dump({'descriptions': new_descriptions, 'file_paths': new_file_paths}, f)
        return True
    except Exception as e:
        print(f"Error: {e}")
        return False

def add_single_to_index(p_id, img_name):
    try:
        img_path = (IMG_DIR / img_name).resolve()
        if not img_path.exists(): return False
        new_feature = extract_features(str(img_path)).reshape(1, -1).astype('float32')

        if not INDEX_PATH.exists() or not META_PATH.exists():
            index = faiss.IndexFlatL2(new_feature.shape[1])
            meta = {'descriptions': [], 'file_paths': []}
        else:
            # 💡 [수정] 우회 읽기 함수 사용
            index = load_faiss_index(str(INDEX_PATH))
            with open(META_PATH, 'rb') as f:
                meta = pickle.load(f)

        safe_count = min(index.ntotal, len(meta.get('descriptions', [])), len(meta.get('file_paths', [])))
        p_id_int = int(p_id)
        
        if p_id_int in meta['descriptions'][:safe_count]:
            indices_to_remove = [i for i, x in enumerate(meta['descriptions'][:safe_count]) if x == p_id_int]
            new_descriptions, new_file_paths, new_features = [], [], []
            
            for i in range(safe_count):
                if i not in indices_to_remove:
                    new_descriptions.append(meta['descriptions'][i])
                    new_file_paths.append(meta['file_paths'][i])
                    new_features.append(index.reconstruct(i))
            
            index = faiss.IndexFlatL2(new_feature.shape[1])
            if new_features:
                index.add(np.array(new_features).astype('float32'))
            
            meta['descriptions'] = new_descriptions
            meta['file_paths'] = new_file_paths
        else:
            meta['descriptions'] = meta['descriptions'][:safe_count]
            meta['file_paths'] = meta['file_paths'][:safe_count]

        index.add(new_feature)
        meta['descriptions'].append(p_id_int)
        meta['file_paths'].append(img_name)
        
        # 💡 [수정] 우회 저장 함수 사용
        save_faiss_index(index, str(INDEX_PATH))
        with open(META_PATH, 'wb') as f:
            pickle.dump(meta, f)
        return True
    except Exception as e:
        print(f"Error: {e}")
        return False

def search_index(query_img_path, top_k=50):
    try:
        if not INDEX_PATH.exists() or not META_PATH.exists(): return []
        
        # 💡 [수정] 우회 읽기 함수 사용
        index = load_faiss_index(str(INDEX_PATH))
        with open(META_PATH, 'rb') as f:
            meta = pickle.load(f)
            
        query_feat = extract_features(query_img_path).reshape(1, -1).astype('float32')
        distances, indices = index.search(query_feat, top_k)
        
        results = []
        for i, idx in enumerate(indices[0]):
            if idx != -1 and idx < len(meta['descriptions']):
                results.append({
                    "rank": i+1, 
                    "product_id": int(meta['descriptions'][idx]), 
                    "score": float(distances[0][i])
                })
        return results
    except Exception as e:
        return []

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--mode', required=True)
    parser.add_argument('--image')
    parser.add_argument('--ids')
    parser.add_argument('--imgs')
    parser.add_argument('--json', action='store_true')
    args = parser.parse_args()
    
    if args.mode == 'build':
        success = build_active_index(args.ids, args.imgs)
        print(json.dumps({"success": success}))
    elif args.mode == 'add_single':
        success = add_single_to_index(args.ids, args.imgs)
        print(json.dumps({"success": success}))
    elif args.mode == 'search':
        res = search_index(args.image)
        print(json.dumps(res))