package com.preonboarding.presentation.view.review

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("bindUrl")
fun ImageView.bindUrl(url: String?) {
    url?.let {
        Glide.with(this)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(20))
            .fitCenter()
            .into(this)
    }
}
