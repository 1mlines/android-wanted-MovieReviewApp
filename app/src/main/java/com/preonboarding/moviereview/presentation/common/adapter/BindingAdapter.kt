package com.preonboarding.moviereview.presentation.common.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.model.ItemType

@BindingAdapter("bindGalleryImage")
fun ImageView.bindGalleryImage(image: GalleryImage) {

    when(image.type) {
        ItemType.CAMERA -> {
            visibility = View.GONE
        }

        ItemType.IMAGE -> {
            Glide.with(this)
                .load(image.imgUri)
                .error(R.drawable.ic_no_image)
                .placeholder(R.drawable.ic_no_image)
                .centerCrop()
                .into(this)
        }
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

@BindingAdapter("posterImage")
fun ImageView.loadPosterImage(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(this)
            .load(imageUrl)
            .error(R.drawable.ic_no_image)
            .placeholder(R.drawable.ic_downloading_24)
            .centerCrop()
            .into(this)
    }
}
