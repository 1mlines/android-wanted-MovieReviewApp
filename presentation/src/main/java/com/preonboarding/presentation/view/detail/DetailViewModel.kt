package com.preonboarding.presentation.view.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.domain.model.MODE
import com.preonboarding.domain.model.Review
import com.preonboarding.domain.model.ReviewUiState
import com.preonboarding.domain.usecase.DeleteReviewUseCase
import com.preonboarding.domain.usecase.GetReviewListUseCase
import com.preonboarding.domain.usecase.UploadReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val uploadReviewUseCase: UploadReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val getReviewListUseCase: GetReviewListUseCase
) : ViewModel() {

    private val _reviewUiState = MutableSharedFlow<ReviewUiState>()
    val reviewUiState = _reviewUiState.asSharedFlow()

    private var _reviewList = MutableStateFlow<List<Review>>(emptyList())
    val reviewList = _reviewList.asStateFlow()

    val passwd = "123456"

    fun uploadReview(title: String, review: Review) {
        viewModelScope.launch {
            runCatching {
                uploadReviewUseCase(title, review)
            }.onSuccess {
                _reviewUiState.emit(ReviewUiState.Success(MODE.REVIEW))
            }.onFailure {
                _reviewUiState.emit(ReviewUiState.Failure(MODE.REVIEW))
            }
        }
    }

    fun deleteReview(title: String, review: Review) {
        viewModelScope.launch {
            runCatching {
                deleteReviewUseCase(title, review)
            }.onSuccess {
                _reviewUiState.emit(ReviewUiState.Success(MODE.DELETE))
            }.onFailure {
                _reviewUiState.emit(ReviewUiState.Failure(MODE.DELETE))
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

    fun checkPasswd(password: String, mode: MODE) {
        viewModelScope.launch {
            if (password == passwd) {
                if (mode == MODE.DELETE) {
                    _reviewUiState.emit(ReviewUiState.Success(mode))
                } else {
                    _reviewUiState.emit(ReviewUiState.Modify)
                }
            } else {
                _reviewUiState.emit(ReviewUiState.Failure(MODE.VALIDATION))
            }
        }
    }

}