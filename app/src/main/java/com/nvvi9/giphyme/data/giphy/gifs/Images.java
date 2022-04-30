package com.nvvi9.giphyme.data.giphy.gifs;

import com.google.gson.annotations.SerializedName;

public final class Images {

    @SerializedName("preview")
    private Preview preview;

    @SerializedName("original")
    private Original original;

    @SerializedName("preview_gif")
    private PreviewGif previewGif;

    @SerializedName("fixed_height_small")
    private FixedHeightSmall fixedHeightSmall;

    @SerializedName("looping")
    private Looping looping;

    @SerializedName("downsized_still")
    private DownsizedStill downsizedStill;

    @SerializedName("fixed_width")
    private FixedWidth fixedWidth;

    @SerializedName("downsized_large")
    private DownsizedLarge downsizedLarge;

    @SerializedName("fixed_height_downsampled")
    private FixedHeightDownsampled fixedHeightDownsampled;

    @SerializedName("downsized_medium")
    private DownsizedMedium downsizedMedium;

    @SerializedName("fixed_height")
    private FixedHeight fixedHeight;

    @SerializedName("fixed_height_small_still")
    private FixedHeightSmallStill fixedHeightSmallStill;

    @SerializedName("fixed_width_downsampled")
    private FixedWidthDownsampled fixedWidthDownsampled;

    @SerializedName("downsized_small")
    private DownsizedSmall downsizedSmall;

    @SerializedName("fixed_width_small")
    private FixedWidthSmall fixedWidthSmall;

    @SerializedName("original_mp4")
    private OriginalMp4 originalMp4;

    @SerializedName("fixed_height_still")
    private FixedHeightStill fixedHeightStill;

    @SerializedName("fixed_width_small_still")
    private FixedWidthSmallStill fixedWidthSmallStill;

    @SerializedName("preview_webp")
    private PreviewWebp previewWebp;

    @SerializedName("fixed_width_still")
    private FixedWidthStill fixedWidthStill;

    @SerializedName("downsized")
    private Downsized downsized;

    @SerializedName("original_still")
    private OriginalStill originalStill;

    @SerializedName("hd")
    private Hd hd;

    public Preview getPreview() {
        return preview;
    }

    public Original getOriginal() {
        return original;
    }

    public PreviewGif getPreviewGif() {
        return previewGif;
    }

    public FixedHeightSmall getFixedHeightSmall() {
        return fixedHeightSmall;
    }

    public Looping getLooping() {
        return looping;
    }

    public DownsizedStill getDownsizedStill() {
        return downsizedStill;
    }

    public FixedWidth getFixedWidth() {
        return fixedWidth;
    }

    public DownsizedLarge getDownsizedLarge() {
        return downsizedLarge;
    }

    public FixedHeightDownsampled getFixedHeightDownsampled() {
        return fixedHeightDownsampled;
    }

    public DownsizedMedium getDownsizedMedium() {
        return downsizedMedium;
    }

    public FixedHeight getFixedHeight() {
        return fixedHeight;
    }

    public FixedHeightSmallStill getFixedHeightSmallStill() {
        return fixedHeightSmallStill;
    }

    public FixedWidthDownsampled getFixedWidthDownsampled() {
        return fixedWidthDownsampled;
    }

    public DownsizedSmall getDownsizedSmall() {
        return downsizedSmall;
    }

    public FixedWidthSmall getFixedWidthSmall() {
        return fixedWidthSmall;
    }

    public OriginalMp4 getOriginalMp4() {
        return originalMp4;
    }

    public FixedHeightStill getFixedHeightStill() {
        return fixedHeightStill;
    }

    public FixedWidthSmallStill getFixedWidthSmallStill() {
        return fixedWidthSmallStill;
    }

    public PreviewWebp getPreviewWebp() {
        return previewWebp;
    }

    public FixedWidthStill getFixedWidthStill() {
        return fixedWidthStill;
    }

    public Downsized getDownsized() {
        return downsized;
    }

    public OriginalStill getOriginalStill() {
        return originalStill;
    }

    public Hd getHd() {
        return hd;
    }

    public static class Preview {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("width")
        private String width;

        @SerializedName("mp4_size")
        private String mp4Size;

        @SerializedName("height")
        private String height;

        public String getMp4() {
            return mp4;
        }

        public String getWidth() {
            return width;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class Downsized {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class DownsizedLarge {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class DownsizedMedium {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class DownsizedSmall {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("width")
        private String width;

        @SerializedName("mp4_size")
        private String mp4Size;

        @SerializedName("height")
        private String height;

        public String getMp4() {
            return mp4;
        }

        public String getWidth() {
            return width;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class DownsizedStill {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedHeight {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("mp4_size")
        private String mp4Size;

        @SerializedName("webp")
        private String webp;

        @SerializedName("webp_size")
        private String webpSize;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getMp4() {
            return mp4;
        }

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public String getWebp() {
            return webp;
        }

        public String getWebpSize() {
            return webpSize;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedHeightDownsampled {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("webp")
        private String webp;

        @SerializedName("webp_size")
        private String webpSize;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getWebp() {
            return webp;
        }

        public String getWebpSize() {
            return webpSize;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedHeightSmall {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("mp4_size")
        private String mp4Size;

        @SerializedName("webp")
        private String webp;

        @SerializedName("webp_size")
        private String webpSize;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getMp4() {
            return mp4;
        }

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public String getWebp() {
            return webp;
        }

        public String getWebpSize() {
            return webpSize;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedHeightSmallStill {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedHeightStill {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedWidth {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("mp4_size")
        private String mp4Size;

        @SerializedName("webp")
        private String webp;

        @SerializedName("webp_size")
        private String webpSize;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getMp4() {
            return mp4;
        }

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public String getWebp() {
            return webp;
        }

        public String getWebpSize() {
            return webpSize;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedWidthDownsampled {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("webp")
        private String webp;

        @SerializedName("webp_size")
        private String webpSize;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getWebp() {
            return webp;
        }

        public String getWebpSize() {
            return webpSize;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedWidthSmall {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("mp4_size")
        private String mp4Size;

        @SerializedName("webp")
        private String webp;

        @SerializedName("webp_size")
        private String webpSize;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getMp4() {
            return mp4;
        }

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public String getWebp() {
            return webp;
        }

        public String getWebpSize() {
            return webpSize;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedWidthSmallStill {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class FixedWidthStill {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class Hd {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("width")
        private String width;

        @SerializedName("mp4_size")
        private String mp4Size;

        @SerializedName("height")
        private String height;

        public String getMp4() {
            return mp4;
        }

        public String getWidth() {
            return width;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class Looping {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("mp4_size")
        private String mp4Size;

        public String getMp4() {
            return mp4;
        }

        public String getMp4Size() {
            return mp4Size;
        }
    }

    public static class Original {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("size")
        private String size;

        @SerializedName("frames")
        private String frames;

        @SerializedName("width")
        private String width;

        @SerializedName("mp4_size")
        private String mp4Size;

        @SerializedName("webp")
        private String webp;

        @SerializedName("webp_size")
        private String webpSize;

        @SerializedName("url")
        private String url;

        @SerializedName("hash")
        private String hash;

        @SerializedName("height")
        private String height;

        public String getMp4() {
            return mp4;
        }

        public String getSize() {
            return size;
        }

        public String getFrames() {
            return frames;
        }

        public String getWidth() {
            return width;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public String getWebp() {
            return webp;
        }

        public String getWebpSize() {
            return webpSize;
        }

        public String getUrl() {
            return url;
        }

        public String getHash() {
            return hash;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class OriginalMp4 {

        @SerializedName("mp4")
        private String mp4;

        @SerializedName("width")
        private String width;

        @SerializedName("mp4_size")
        private String mp4Size;

        @SerializedName("height")
        private String height;

        public String getMp4() {
            return mp4;
        }

        public String getWidth() {
            return width;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class OriginalStill {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class PreviewGif {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class PreviewWebp {

        @SerializedName("size")
        private String size;

        @SerializedName("width")
        private String width;

        @SerializedName("url")
        private String url;

        @SerializedName("height")
        private String height;

        public String getSize() {
            return size;
        }

        public String getWidth() {
            return width;
        }

        public String getUrl() {
            return url;
        }

        public String getHeight() {
            return height;
        }
    }
}