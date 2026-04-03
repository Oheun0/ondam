import os
import argparse
from PIL import Image

# 1. Pillow compatibility patch
if not hasattr(Image, 'ANTIALIAS'):
    Image.ANTIALIAS = getattr(Image, 'LANCZOS', getattr(Image.Resampling, 'LANCZOS', 1))

# 2. ImageMagick Path (Please check your installed version)
os.environ["IMAGEMAGICK_BINARY"] = r"C:\Program Files\ImageMagick-7.1.2-Q16-HDRI\magick.exe"

from moviepy.editor import (
    ImageClip,
    concatenate_videoclips,
    TextClip,
    CompositeVideoClip,
    AudioFileClip
)

def create_shorts(image_paths, product_name, output_path, font_path, audio_path=None):
    # --- 1. Settings (Fixed 10s) ---
    target_resolution = (1080, 1920)
    zoom_rate = 0.03
    total_duration = 10.0  
    clips = []

    image_paths = image_paths[:3]
    
    # Check if files exist
    valid_image_paths = []
    for img in image_paths:
        if os.path.exists(img):
            valid_image_paths.append(img)
        else:
            print(f"[Warning] Image not found: {img}")
    
    if not valid_image_paths:
        print("[Error] No valid images found to process.")
        return False
        
    # --- 2. Calculate duration per image ---
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

    # 3. Concatenate clips
    base_video = concatenate_videoclips(clips, method="compose")

    # 4. Create Subtitle (Product Name)
    actual_font = font_path if os.path.exists(font_path) else 'Arial'
    
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
        print(f"[Error] TextClip creation failed: {e}")
        return False

    txt_clip = (txt_clip.set_position(('center', 0.6), relative=True)
                        .set_duration(base_video.duration)
                        .crossfadein(0.5)
                        .crossfadeout(0.5))

    # 5. Composite video and text
    final_video = CompositeVideoClip([base_video, txt_clip])

    # 6. Audio (Optional)
    if audio_path and os.path.exists(audio_path):
        audio = AudioFileClip(audio_path).subclip(0, final_video.duration)
        final_video = final_video.set_audio(audio)

    # --- 7. Create directory and Thumbnail ---
    output_dir = os.path.dirname(output_path)
    if output_dir and not os.path.exists(output_dir):
        os.makedirs(output_dir, exist_ok=True)

    # Thumbnail extraction (RGBA to RGB)
    thumb_path = output_path.replace(".mp4", "_thumb.jpg")
    frame = final_video.get_frame(0.0) 
    img = Image.fromarray(frame)
    if img.mode in ("RGBA", "P"): 
        img = img.convert("RGB")
    img.save(thumb_path)
    print(f"[Info] Thumbnail generated: {thumb_path}")

    # 8. Rendering
    print(f"[Info] Starting video rendering: {output_path}")
    final_video.write_videofile(
        output_path,
        fps=30,
        codec="libx264",
        audio_codec="aac",
        threads=4,
        preset="ultrafast",
        logger=None
    )
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