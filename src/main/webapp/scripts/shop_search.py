import os, sys, argparse, faiss, pickle, torch, json
import numpy as np
from PIL import Image
from pathlib import Path
from torchvision import transforms, models

BASE_DIR = Path(__file__).resolve().parent
IMG_DIR = (BASE_DIR / "../uploads/products").resolve()
INDEX_PATH = (BASE_DIR / "../uploads/index/shop_index.faiss").resolve()
META_PATH = (BASE_DIR / "../uploads/index/shop_meta.pkl").resolve()

weights = models.ResNet50_Weights.DEFAULT
model = models.resnet50(weights=weights)
model.eval()
preprocess = transforms.Compose([
    transforms.Resize(256), transforms.CenterCrop(224),
    transforms.ToTensor(), transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
])

def extract_features(img_path):
    img = Image.open(img_path).convert('RGB')
    input_tensor = preprocess(img).unsqueeze(0)
    with torch.no_grad():
        feature = model(input_tensor)
    return feature.numpy().flatten()

def build_active_index():
    print(f"[Python Debug] Scanning {IMG_DIR}")
    if not IMG_DIR.exists():
        print(f"[Python Error] Image directory does not exist: {IMG_DIR}")
        return False
    
    os.makedirs(INDEX_PATH.parent, exist_ok=True)
    new_descriptions, new_file_paths, features_list = [], [], []
    
    valid_images = [f for f in IMG_DIR.glob("*.jpg") if "_main" in f.name]
    print(f"[Python Debug] Found {len(valid_images)} main images")
    
    for img_path in valid_images:
        try:
            # 파일명 형식 체크 (product_101_main.jpg 예상)
            parts = img_path.name.split('_')
            if len(parts) < 2: continue
            
            p_id = int(parts[1])
            features_list.append(extract_features(str(img_path)))
            new_descriptions.append(p_id)
            new_file_paths.append(img_path.name)
        except Exception as e:
            print(f"[Python Warning] Failed to process {img_path.name}: {str(e)}")
            continue
            
    if not features_list:
        print("[Python Error] No features extracted. Index not created.")
        return False
        
    features_np = np.vstack(features_list).astype('float32')
    index = faiss.IndexFlatL2(features_np.shape[1])
    index.add(features_np)
    
    faiss.write_index(index, str(INDEX_PATH))
    with open(META_PATH, 'wb') as f:
        pickle.dump({'descriptions': new_descriptions, 'file_paths': new_file_paths}, f)
    print(f"[Python Success] Index built with {len(new_descriptions)} items.")
    return True

def search_index(query_img_path, top_k=15):
    if not INDEX_PATH.exists(): 
        print(f"[Python Error] Index file not found at: {INDEX_PATH}")
        return {"error": "Index file missing"}
        
    index = faiss.read_index(str(INDEX_PATH))
    with open(META_PATH, 'rb') as f:
        meta = pickle.load(f)
    
    print(f"[Python Debug] Searching with query image: {query_img_path}")
    query_feat = extract_features(query_img_path).reshape(1, -1).astype('float32')
    distances, indices = index.search(query_feat, top_k)
    
    results = []
    for i, idx in enumerate(indices[0]):
        if idx < len(meta['descriptions']):
            results.append({"rank": i+1, "product_id": str(meta['descriptions'][idx]), "score": float(distances[0][i])})
    return results

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--mode', required=True)
    parser.add_argument('--image')
    parser.add_argument('--json', action='store_true')
    args = parser.parse_args()
    
    try:
        if args.mode == 'build':
            success = build_active_index()
            print(json.dumps({"success": success}))
        elif args.mode == 'search':
            if not args.image:
                print(json.dumps({"error": "No image path provided"}))
            else:
                res = search_index(args.image)
                print(json.dumps(res))
    except Exception as e:
        # 에러 발생 시 Traceback을 포함하여 상세 출력
        error_msg = traceback.format_exc()
        print(f"[Python Critical Error]\n{error_msg}")
        print(json.dumps({"error": str(e), "full_trace": error_msg}))