package com.preonboarding.presentation.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.domain.model.EditState
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


//TODO 영화제목 받아와야함
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

    private val _selectedReview = MutableStateFlow(Review())
    val selectedReview = _selectedReview.asStateFlow()

    var reviewBuffer = Review()

    var editState = EditState()

    val passwd = "123456"

    var uri: String = ""

    // 영화제목 받아와야함
    fun uploadReview(title: String) {
        viewModelScope.launch {
            runCatching {
                uploadReviewUseCase(title, reviewBuffer)
            }.onSuccess {
                _reviewUiState.emit(ReviewUiState.Success(MODE.REVIEW))
            }.onFailure {
                _reviewUiState.emit(ReviewUiState.Failure(MODE.REVIEW))
            }
        }
    }

    // 영화제목 받아와야함
    fun setMovieList(title: String) {
        viewModelScope.launch {
            getReviewListUseCase(title).stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                emptyList()
            ).collectLatest {
                _reviewList.emit(it)
            }
        }

    }

    // 영화제목 받아와야함
    fun deleteReview(title: String) {
        viewModelScope.launch {
            runCatching {
                deleteReviewUseCase(title, reviewBuffer)
            }.onSuccess {
                _reviewUiState.emit(ReviewUiState.Success(MODE.DELETE))
            }.onFailure {
                _reviewUiState.emit(ReviewUiState.Failure(MODE.DELETE))
            }
        }
    }

    fun checkPasswd(password: String, mode: MODE) {
        viewModelScope.launch {
            if (password == passwd) {
                if (mode == MODE.DELETE) {
                    // 리뷰 삭제 로직
                    // deleteReview()
                    _reviewUiState.emit(ReviewUiState.Success(mode))
                } else {
                    _reviewUiState.emit(ReviewUiState.Modify)
                }
            } else {
                _reviewUiState.emit(ReviewUiState.Failure(MODE.VALIDATION))
            }
        }
    }

    fun changeUiState(uiState: ReviewUiState) {
        viewModelScope.launch {
            _reviewUiState.emit(uiState)
        }
    }

    fun initSelectedReview() {
        viewModelScope.launch {
            _selectedReview.value = Review()
        }
    }

}