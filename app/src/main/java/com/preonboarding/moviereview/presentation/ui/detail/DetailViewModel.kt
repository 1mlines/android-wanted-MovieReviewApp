package com.preonboarding.moviereview.presentation.ui.detail

import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.data.remote.model.MoviePosterResponse
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import com.preonboarding.moviereview.presentation.common.base.BaseViewModel
import com.preonboarding.moviereview.presentation.common.const.KOBIS_API_KEY
import com.preonboarding.moviereview.presentation.common.const.OMDB_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
) : BaseViewModel() {

    private val _movieInfo: MutableStateFlow<MovieStatus> =
        MutableStateFlow(MovieStatus.Loading)
    val movieInfo: StateFlow<MovieStatus>
        get() = _movieInfo

    private val _moviePoster: MutableStateFlow<MoviePosterStatus> =
        MutableStateFlow(MoviePosterStatus.Loading)
    val moviePoster: StateFlow<MoviePosterStatus>
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
            ).collect {
                _movieInfo.value = MovieStatus.Success(it)
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
