package com.preonboarding.presentation.common.shared

import android.view.View
import androidx.databinding.BindingAdapter
import com.preonboarding.presentation.common.utils.ThrottleClickListener

@BindingAdapter("onThrottleClick")
fun View.setOnThrottleClickListener(listener: View.OnClickListener) {
    setOnClickListener(
        ThrottleClickListener {
            listener.onClick(it)
        }
    )
}