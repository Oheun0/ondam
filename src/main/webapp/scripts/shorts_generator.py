import os
import argparse
import shutil
from PIL import Image

# 💡 [추가] 진행률 출력을 위한 프로그레스 로거 임포트
from proglog import ProgressBarLogger

if not hasattr(Image, 'ANTIALIAS'):
    Image.ANTIALIAS = getattr(Image, 'LANCZOS', getattr(Image.Resampling, 'LANCZOS', 1))

magick_path = shutil.which("magick") or shutil.which("convert")
if magick_path:
    os.environ["IMAGEMAGICK_BINARY"] = magick_path
elif os.name == 'nt':
    os.environ["IMAGEMAGICK_BINARY"] = r"C:\Program Files\ImageMagick-7.1.2-Q16-HDRI\magick.exe"
else:
    os.environ["IMAGEMAGICK_BINARY"] = "/usr/bin/convert"

from moviepy.editor import (
    ImageClip,
    concatenate_videoclips,
    TextClip,
    CompositeVideoClip,
    AudioFileClip
)

# 💡 [추가] 자바 콘솔창에 진행률을 10% 단위로 깔끔하게 쏴주는 커스텀 로거
class CleanConsoleLogger(ProgressBarLogger):
    def __init__(self):
        super().__init__()
        self.last_percent = -1

    def bars_callback(self, bar, attr, value, old_value=None):
        total = self.bars[bar]['total']
        if total > 0:
            percent = int((value / total) * 100)
            # 10% 단위로 진행될 때만 콘솔에 출력 (flush=True로 자바 쪽으로 즉시 전송)
            if percent % 10 == 0 and percent != self.last_percent:
                print(f"[Render Progress] 렌더링 진행률: {percent}%", flush=True)
                self.last_percent = percent

def create_shorts(image_paths, product_name, output_path, font_path, audio_path=None):
    # 💡 [수정] 렌더링 속도 향상을 위해 1080p -> 720p 로 해상도 다운사이징
    target_resolution = (720, 1280) 
    zoom_rate = 0.03
    duration_per_image = 5.0  
    clips = []

    valid_image_paths = [img for img in image_paths if os.path.exists(img)]
    valid_image_paths = valid_image_paths[:6] 
    
    if not valid_image_paths:
        print("[Error] No valid images found to process.")
        return False
        
    total_duration = len(valid_image_paths) * duration_per_image
    print(f"[Info] 사용 이미지: {len(valid_image_paths)}장, 영상 길이: {total_duration}초 (해상도: 720p)")
    
    for img_path in valid_image_paths:
        base_clip = ImageClip(img_path).set_duration(duration_per_image)
        base_clip = base_clip.resize(width=target_resolution[0])
        animated_clip = base_clip.resize(lambda t: 1 + zoom_rate * t)
        final_clip = animated_clip.set_position(('center', 'center')).on_color(
            size=target_resolution,
            color=(0, 0, 0),
            col_opacity=1
        )
        clips.append(final_clip)

    base_video = concatenate_videoclips(clips, method="compose")

    actual_font = font_path if os.path.exists(font_path) else ('Arial' if os.name == 'nt' else 'Liberation-Sans')
    
    try:
        txt_clip = TextClip(
            product_name,
            fontsize=60, # 💡 720p에 맞게 폰트 크기 조정 (80 -> 60)
            color='white',
            font=actual_font,
            stroke_color='black',
            stroke_width=2 # 스트로크 굵기도 살짝 줄임
        )
    except Exception as e:
        print(f"[Error] TextClip creation failed. Check ImageMagick installation: {e}")
        return False

    txt_clip = (txt_clip.set_position(('center', 0.6), relative=True)
                        .set_duration(base_video.duration)
                        .crossfadein(0.5)
                        .crossfadeout(0.5))

    final_video = CompositeVideoClip([base_video, txt_clip])

    if audio_path and os.path.exists(audio_path):
        try:
            audio = AudioFileClip(audio_path)
            audio_duration = min(audio.duration, final_video.duration)
            audio = audio.subclip(0, audio_duration)
            final_video = final_video.set_audio(audio)
            print(f"[Info] Background music added successfully.")
        except Exception as e:
            print(f"[Warning] Background music failed: {e}")

    output_dir = os.path.dirname(output_path)
    if output_dir:
        os.makedirs(output_dir, exist_ok=True)

    thumb_path = output_path.replace(".mp4", "_thumb.jpg")
    try:
        frame = final_video.get_frame(0.0) 
        img = Image.fromarray(frame)
        if img.mode in ("RGBA", "P"): 
            img = img.convert("RGB")
        img.save(thumb_path, optimize=True, quality=80) # 썸네일 용량 압축률 상승
    except Exception as e:
        print(f"[Warning] Failed to generate thumbnail: {e}")

    print(f"[Info] Starting video rendering...")
    try:
        # 💡 [추가] 커스텀 로거 적용
        custom_logger = CleanConsoleLogger()
        
        final_video.write_videofile(
            output_path,
            fps=30, # 24로 낮추면 더 빨라지지만, 영상이 끊겨 보일 수 있어 30 유지
            codec="libx264",
            audio_codec="aac",
            threads=4,
            preset="ultrafast",
            logger=custom_logger # 💡 기본 logger=None을 해제하고 커스텀 로거 장착
        )
    except Exception as e:
        print(f"[Error] Render failed: {e}")
        return False
    finally:
        final_video.close()
        
    return True

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Ondam Shorts Generator")
    parser.add_argument("--images", required=True)
    parser.add_argument("--text", required=True)
    parser.add_argument("--output", required=True)
    parser.add_argument("--font", default="C:/Windows/Fonts/malgun.ttf")
    parser.add_argument("--audio", default=None)

    args = parser.parse_args()
    img_list = [img.strip() for img in args.images.split(",")]

    success = create_shorts(
        image_paths=img_list,
        product_name=args.text,
        output_path=args.output,
        font_path=args.font,
        audio_path=args.audio
    )

    if success:
        print("[Success] Shorts generation completed.")
        exit(0)
    else:
        print("[Error] Shorts generation failed.")
        exit(1)