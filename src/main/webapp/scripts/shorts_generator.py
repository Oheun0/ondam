import os
import argparse
import shutil
from PIL import Image

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

def create_shorts(image_paths, product_name, output_path, font_path, audio_path=None):
    target_resolution = (1080, 1920)
    zoom_rate = 0.03
    total_duration = 10.0  
    clips = []

    # 유효한 이미지만 필터링 후 최대 3장까지 자름
    valid_image_paths = [img for img in image_paths if os.path.exists(img)]
    valid_image_paths = valid_image_paths[:3] 
    
    if not valid_image_paths:
        print("[Error] No valid images found to process.")
        return False
        
    # 이미지가 1장이면 10초, 2장이면 5초씩 동적 분배
    duration_per_image = total_duration / len(valid_image_paths)
    
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
            fontsize=80,
            color='white',
            font=actual_font,
            stroke_color='black',
            stroke_width=3
        )
    except Exception as e:
        print(f"[Error] TextClip creation failed. Check ImageMagick installation: {e}")
        return False

    txt_clip = (txt_clip.set_position(('center', 0.6), relative=True)
                        .set_duration(base_video.duration)
                        .crossfadein(0.5)
                        .crossfadeout(0.5))

    final_video = CompositeVideoClip([base_video, txt_clip])

    # 💡 [수정된 로직] 배경음악 삽입 시 영상 길이(10초)보다 짧은 경우 에러가 나지 않도록 처리
    if audio_path and os.path.exists(audio_path):
        try:
            audio = AudioFileClip(audio_path)
            # 음악과 영상 중 더 짧은 시간에 맞춤 (음악이 짧아도 에러 안나게)
            audio_duration = min(audio.duration, final_video.duration)
            audio = audio.subclip(0, audio_duration)
            final_video = final_video.set_audio(audio)
            print(f"[Info] Background music added successfully: {audio_path}")
        except Exception as e:
            print(f"[Warning] Background music failed to load. Proceeding without audio: {e}")

    output_dir = os.path.dirname(output_path)
    if output_dir:
        os.makedirs(output_dir, exist_ok=True)

    thumb_path = output_path.replace(".mp4", "_thumb.jpg")
    try:
        frame = final_video.get_frame(0.0) 
        img = Image.fromarray(frame)
        if img.mode in ("RGBA", "P"): 
            img = img.convert("RGB")
        img.save(thumb_path, optimize=True, quality=85)
        print(f"[Info] Thumbnail generated: {thumb_path}")
    except Exception as e:
        print(f"[Warning] Failed to generate thumbnail: {e}")

    print(f"[Info] Starting video rendering: {output_path}")
    try:
        final_video.write_videofile(
            output_path,
            fps=30,
            codec="libx264",
            audio_codec="aac",
            threads=4,
            preset="ultrafast",
            logger=None
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
        print("[Success] 10s Shorts generation completed.")
        exit(0)
    else:
        print("[Error] Shorts generation failed.")
        exit(1)