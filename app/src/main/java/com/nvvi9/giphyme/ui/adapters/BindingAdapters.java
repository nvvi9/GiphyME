package com.nvvi9.giphyme.ui.adapters;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void setGlideUrl(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .placeholder(getShimmer())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    @BindingAdapter("gifUrl")
    public static void setGifUrl(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(imageView.getContext())
                    .asGif()
                    .load(url)
                    .placeholder(getShimmer())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    @BindingAdapter("date")
    public static void setDate(TextView textView, Date date) {
        if (date != null) {
            String formattedDate = DateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.US).format(date);
            textView.setText(formattedDate);
        }
    }

    private static ShimmerDrawable getShimmer() {
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();

        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        return shimmerDrawable;
    }
}
