package com.preonboarding.presentation.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.domain.model.Review
import com.preonboarding.domain.usecase.DeleteReviewUseCase
import com.preonboarding.domain.usecase.GetReviewListUseCase
import com.preonboarding.domain.usecase.UpdateReviewUseCase
import com.preonboarding.domain.usecase.UploadReviewUseCase
import com.preonboarding.presentation.view.review.EditState
import com.preonboarding.presentation.view.review.MODE
import com.preonboarding.presentation.view.review.ReviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO 영화제목 받아와야함
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val uploadReviewUseCase: UploadReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val getReviewListUseCase: GetReviewListUseCase,
    private val updateReviewUseCase: UpdateReviewUseCase
) : ViewModel() {

    private val _reviewUiState = MutableSharedFlow<ReviewUiState>()
    val reviewUiState = _reviewUiState.asSharedFlow()

    private var _reviewList = MutableStateFlow<List<Review>>(emptyList())
    val reviewList = _reviewList.asStateFlow()

    private val _selectedReview = MutableStateFlow(Review())
    val selectedReview = _selectedReview.asStateFlow()

    private var getReviewListJob: Job? = null

    var reviewBuffer = Review()

    var editState = EditState()

    //TODO 수정필요
    var passwd = "123456"

    var uri: String = ""

    lateinit var title: String

    fun updateReview(title: String){
        viewModelScope.launch {
            runCatching {
                updateReviewUseCase(title, reviewBuffer)
            }.onSuccess {
                _reviewUiState.emit(ReviewUiState.Success(MODE.REVIEW))
            }.onFailure {
                _reviewUiState.emit(ReviewUiState.Failure(MODE.REVIEW))
            }
        }
    }


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


    fun setMovieList(title: String) {
        getReviewListJob?.cancel()
        getReviewListJob = viewModelScope.launch {
            getReviewListUseCase(title).stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                emptyList()
            ).collectLatest {
                _reviewList.emit(it)
            }
        }

    }


    fun deleteReview(title: String) {
        viewModelScope.launch {
            runCatching {
                //TODO 모든 리뷰가 삭제되는듯 , 모두 같은 비밀번호 123456을 공유해서 인거같습니다.
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
            //TODO 비밀번호 값 가져와야함!
            if (password == passwd) {
                if (mode == MODE.DELETE) {
                    deleteReview(title)
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