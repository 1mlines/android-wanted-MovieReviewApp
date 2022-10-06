package com.preonboarding.moviereview.presentation.ui.detail

import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import com.preonboarding.moviereview.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
) : BaseViewModel() {

    fun searchReviewMovieList(movieId : Int) {
        viewModelScope.launch {
            remoteRepository.searchReviewInfo(
                movieId = movieId
            )
                .collect {
                    Timber.tag("ReviewModel").e(it.toString())
                }
        }
    }
}