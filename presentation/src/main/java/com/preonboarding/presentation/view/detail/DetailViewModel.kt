package com.preonboarding.presentation.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.domain.model.Review
import com.preonboarding.domain.model.ReviewUiState
import com.preonboarding.domain.usecase.DeleteReviewUseCase
import com.preonboarding.domain.usecase.GetReviewListUseCase
import com.preonboarding.domain.usecase.UploadReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val uploadReviewUseCase: UploadReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val getReviewListUseCase: GetReviewListUseCase
) : ViewModel() {

    private val _reviewUiState = MutableStateFlow<ReviewUiState>(ReviewUiState.Empty)
    val reviewUiState = _reviewUiState.asStateFlow()

    private var _reviewList = MutableStateFlow<List<Review>>(emptyList())
    val reviewList = _reviewList.asStateFlow()

    fun uploadReview(title: String, review: Review) {
        viewModelScope.launch {
            runCatching {
                uploadReviewUseCase(title, review)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    fun deleteReview(title: String, review: Review) {
        viewModelScope.launch {
            runCatching {
                deleteReviewUseCase(title, review)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    fun getReviewList(title: String) {
        _reviewList = getReviewListUseCase(title)
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                emptyList()
            ) as MutableStateFlow<List<Review>>
    }

}