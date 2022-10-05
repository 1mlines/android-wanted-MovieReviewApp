package com.preonboarding.moviereview.presentation.common.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.preonboarding.moviereview.R

@BindingAdapter("bindGalleryImage")
fun ImageView.bindGalleryImage(imgUrl: String?) {
    imgUrl?.let {
        Glide.with(this)
            .load(imgUrl)
            .error(R.drawable.ic_no_image)
            .placeholder(R.drawable.ic_no_image)
            .centerCrop()
            .into(this)
    }
}