package com.preonboarding.moviereview.presentation.ui.detail

import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.data.remote.model.MovieInfo
import com.preonboarding.moviereview.data.remote.model.MoviePosterResponse
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import com.preonboarding.moviereview.presentation.common.base.BaseViewModel
import com.preonboarding.moviereview.presentation.common.const.KOBIS_API_KEY
import com.preonboarding.moviereview.presentation.common.const.OMDB_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
) : BaseViewModel() {

    private val _movieInfo: MutableStateFlow<MovieInfo> =
        MutableStateFlow() //초기값?
    val movieInfo: StateFlow<MovieInfo>
        get() = _movieInfo

    private val _moviePoster: MutableStateFlow<List<MoviePosterResponse>> =
        MutableStateFlow(emptyList<MoviePosterResponse>()) // 초기값?
    val moviePoster: StateFlow<List<MoviePosterResponse>>
        get() = _moviePoster

    fun searchReviewMovieList(movieId: Int) {
        viewModelScope.launch {
            remoteRepository.searchReviewInfo(
                movieId = movieId
            )
                .collect {
                    Timber.tag("ReviewModel").e(it.toString())
                }
        }
    }

    fun fetchMovieDetail(movieCd: String?) {
        viewModelScope.launch {
            remoteRepository.searchMovieInfo(
                key = KOBIS_API_KEY,
                movieCd = movieCd
            )
                .collect {
                    _movieInfo.value = it.movieInfoResult.movieInfo
                }
        }
    }

    fun fetchPoster(title: String) {
        viewModelScope.launch {
            remoteRepository.getMoviePoster(
                key = OMDB_API_KEY,
                title = title
            )
                .collect {
                    //TODO: get poster
                }
        }

    }
}