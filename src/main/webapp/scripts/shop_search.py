import os, sys, argparse, faiss, pickle, torch, json
import numpy as np
from PIL import Image
from pathlib import Path
from torchvision import transforms, models

# 1. 경로 설정
BASE_DIR = Path(__file__).resolve().parent
IMG_DIR = (BASE_DIR / "../uploads/products").resolve()
INDEX_PATH = (BASE_DIR / "../uploads/index/shop_index.faiss").resolve()
META_PATH = (BASE_DIR / "../uploads/index/shop_meta.pkl").resolve()

# 2. 인덱스 폴더 생성 확인
os.makedirs(os.path.dirname(INDEX_PATH), exist_ok=True)

# 3. 모델 설정 (ResNet50)
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
    """이미지 파일에서 특징 벡터 추출"""
    img = Image.open(img_path).convert('RGB')
    input_tensor = preprocess(img).unsqueeze(0)
    with torch.no_grad():
        feature = model(input_tensor)
    return feature.numpy().flatten()

def build_active_index(id_list_str, img_list_str):
    """💡 [수정됨] 자바가 넘겨준 DB 대표 이미지 명단만 인덱싱 (전체 재생성)"""
    try:
        new_descriptions, new_file_paths, features_list = [], [], []
        
        # 콤마로 구분된 명단을 리스트로 쪼갬
        p_ids = [int(x) for x in id_list_str.split(',')]
        img_names = [x.strip() for x in img_list_str.split(',')]
        
        for p_id, img_name in zip(p_ids, img_names):
            img_path = (IMG_DIR / img_name).resolve()
            if img_path.exists():
                try:
                    feat = extract_features(str(img_path))
                    features_list.append(feat)
                    new_descriptions.append(p_id)      # 무조건 정확한 productNo 저장!
                    new_file_paths.append(img_name)
                except Exception as e:
                    print(f"Error extracting {img_name}: {e}")
                    
        if not features_list: return False
        
        features_np = np.vstack(features_list).astype('float32')
        index = faiss.IndexFlatL2(features_np.shape[1])
        index.add(features_np)
        
        faiss.write_index(index, str(INDEX_PATH))
        with open(META_PATH, 'wb') as f:
            pickle.dump({'descriptions': new_descriptions, 'file_paths': new_file_paths}, f)
        return True
    except Exception as e:
        print(f"Error: {e}")
        return False

def add_single_to_index(p_id, img_name):
    """특정 상품의 이미지를 추가하거나, 기존 이미지가 있다면 안전하게 교체합니다."""
    try:
        # 1. 새 이미지 특징 우선 추출
        img_path = (IMG_DIR / img_name).resolve()
        if not img_path.exists(): return False
        new_feature = extract_features(str(img_path)).reshape(1, -1).astype('float32')

        # 2. 인덱스 로드 또는 새로 생성
        if not INDEX_PATH.exists() or not META_PATH.exists():
            index = faiss.IndexFlatL2(new_feature.shape[1])
            meta = {'descriptions': [], 'file_paths': []}
        else:
            index = faiss.read_index(str(INDEX_PATH))
            with open(META_PATH, 'rb') as f:
                meta = pickle.load(f)

        # 3. 💡 [핵심 수정] 파일이 꼬여서 FAISS와 PKL의 개수가 다를 때를 대비한 안전 장치(최솟값 기준)
        safe_count = min(index.ntotal, len(meta.get('descriptions', [])), len(meta.get('file_paths', [])))
        
        p_id_int = int(p_id)
        
        # 안전한 범위(safe_count) 내에서만 중복 검사
        if p_id_int in meta['descriptions'][:safe_count]:
            indices_to_remove = [i for i, x in enumerate(meta['descriptions'][:safe_count]) if x == p_id_int]
            
            new_descriptions = []
            new_file_paths = []
            new_features = []
            
            for i in range(safe_count):
                if i not in indices_to_remove:
                    new_descriptions.append(meta['descriptions'][i])
                    new_file_paths.append(meta['file_paths'][i])
                    new_features.append(index.reconstruct(i))
            
            # 필터링된 데이터로 인덱스 재초기화
            index = faiss.IndexFlatL2(new_feature.shape[1])
            if new_features:
                index.add(np.array(new_features).astype('float32'))
            
            meta['descriptions'] = new_descriptions
            meta['file_paths'] = new_file_paths
        else:
            # 중복이 없더라도 혹시 모를 꼬임을 방지하기 위해 길이를 일치시킴
            meta['descriptions'] = meta['descriptions'][:safe_count]
            meta['file_paths'] = meta['file_paths'][:safe_count]

        # 4. 새 데이터 추가
        index.add(new_feature)
        meta['descriptions'].append(p_id_int)
        meta['file_paths'].append(img_name)
        
        # 5. 파일로 저장
        faiss.write_index(index, str(INDEX_PATH))
        with open(META_PATH, 'wb') as f:
            pickle.dump(meta, f)
        return True
    except Exception as e:
        # 에러 발생 시 상세 이유 출력
        import traceback
        print(f"Error: {e}")
        traceback.print_exc() 
        return False

def search_index(query_img_path, top_k=10):
    """유사 상품 검색"""
    try:
        if not INDEX_PATH.exists() or not META_PATH.exists():
            print(f"[Python Error] 인덱스 파일이 없습니다: {INDEX_PATH}")
            return []
            
        index = faiss.read_index(str(INDEX_PATH))
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
        print(f"[Python Critical Error] 검색 중 오류 발생: {str(e)}")
        return []

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--mode', required=True)
    parser.add_argument('--image')
    parser.add_argument('--ids') # productNo 전달용
    parser.add_argument('--imgs') # imgFile 전달용
    parser.add_argument('--json', action='store_true')
    args = parser.parse_args()
    
    if args.mode == 'build':
        # 💡 [수정됨] 자바에서 넘긴 파라미터를 넘겨받아 실행
        success = build_active_index(args.ids, args.imgs)
        print(json.dumps({"success": success}))
    elif args.mode == 'add_single':
        success = add_single_to_index(args.ids, args.imgs)
        print(json.dumps({"success": success}))
    elif args.mode == 'search':
        res = search_index(args.image)
        print(json.dumps(res))