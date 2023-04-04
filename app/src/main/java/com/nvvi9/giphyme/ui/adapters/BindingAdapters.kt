package com.nvvi9.giphyme.ui.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.Shimmer.AlphaHighlightBuilder
import com.facebook.shimmer.ShimmerDrawable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun ImageView.setGlideUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .load(url)
            .placeholder(shimmer)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}

@BindingAdapter("gifUrl")
fun ImageView.setGifUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .asGif()
            .load(url)
            .placeholder(shimmer)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}

@BindingAdapter("date")
fun TextView.setDate(date: Date?) {
    date?.let {
        val formattedDate =
            DateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.US).format(date)
        text = formattedDate
    }
}

private val shimmer: ShimmerDrawable
    get() {
        val shimmer = AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()
        val shimmerDrawable = ShimmerDrawable()
        shimmerDrawable.setShimmer(shimmer)
        return shimmerDrawable
    }