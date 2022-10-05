package com.preonboarding.moviereview.presentation.common.adapter

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.preonboarding.moviereview.R

@BindingAdapter("bindGalleryImage")
fun ImageView.bindGalleryImage(imgUrl: Uri?) {
    imgUrl?.let {
        Glide.with(this)
            .load(imgUrl)
            .error(R.drawable.ic_no_image)
            .placeholder(R.drawable.ic_no_image)
            .centerCrop()
            .into(this)
    }
}

@BindingAdapter("bindReviewImage")
fun ImageView.bindReviewImage(imgUrl: Uri?) {
    imgUrl?.let {
        Glide.with(this)
            .load(imgUrl)
            .centerCrop()
            .into(this)
    }
}