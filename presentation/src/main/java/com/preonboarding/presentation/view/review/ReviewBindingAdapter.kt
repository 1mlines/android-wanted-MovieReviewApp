package com.preonboarding.presentation.view.review

import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.preonboarding.domain.model.EditState

@BindingAdapter("bindUri")
fun ImageView.bindUri(uri: String?) {
    uri?.let {
        if (uri.isNotBlank()) {
            Glide.with(this)
                .load(it)
                .transform(CenterCrop(), RoundedCorners(20))
                .fitCenter()
                .into(this)
        }
    }
}

@BindingAdapter("bindEditableMode")
fun EditText.bindEditableMode(editState: EditState) {
    if (editState.isEditable) {
        isEnabled = !editState.isModifyMode
    } else {
        isEnabled = false
    }
}

@BindingAdapter("bindTextEditableMode")
fun EditText.bindTextEditableMode(editState: EditState) {
    isEnabled = editState.isEditable
}

@BindingAdapter("bindButtonEditableMode")
fun AppCompatButton.bindButtonEditableMode(editState: EditState) {
    isVisible = editState.isEditable
}

@BindingAdapter("bindRatingBarEditableMode")
fun RatingBar.bindRatingBarEditableMode(editState: EditState) {
    isEnabled = editState.isEditable
}

@BindingAdapter("bindImageViewEditableMode")
fun ImageView.bindImageViewEditableMode(editState: EditState) {
    isClickable = editState.isEditable
}

@BindingAdapter("bindRatingBar")
fun RatingBar.bindRatingBar(float: Float) {
    rating = float
}

@BindingAdapter("bindString")
fun EditText.bindString(content: String) {
    setText(content)
}