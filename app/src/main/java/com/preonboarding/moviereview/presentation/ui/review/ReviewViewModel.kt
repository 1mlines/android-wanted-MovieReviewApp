package com.preonboarding.moviereview.presentation.ui.review

import android.net.Uri
import com.preonboarding.moviereview.presentation.common.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ReviewViewModel: BaseViewModel() {

    private val _reviewImageUri = MutableStateFlow<Uri>(Uri.EMPTY)
    val reviewImageUri: MutableStateFlow<Uri>
        get() = _reviewImageUri

}