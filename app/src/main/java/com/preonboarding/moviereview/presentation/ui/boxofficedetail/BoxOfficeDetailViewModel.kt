package com.preonboarding.moviereview.presentation.ui.boxofficedetail

import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import com.preonboarding.moviereview.presentation.common.base.BaseViewModel
import com.preonboarding.moviereview.presentation.common.const.KOBIS_API_KEY
import com.preonboarding.moviereview.presentation.common.const.OMDB_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxOfficeDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
) : BaseViewModel() {

    private val _movieInfo: MutableStateFlow<MovieStatus> =
        MutableStateFlow(MovieStatus.Initial)
    val movieInfo: SharedFlow<MovieStatus>
        get() = _movieInfo

    private val _basicInfo: MutableStateFlow<MovieBasicStatus> =
        MutableStateFlow(MovieBasicStatus.Initial)
    val basicInfo: SharedFlow<MovieBasicStatus>
        get() = _basicInfo

    private val _moviePoster: MutableStateFlow<MoviePosterStatus> =
        MutableStateFlow(MoviePosterStatus.Initial)
    val moviePoster: StateFlow<MoviePosterStatus>
        get() = _moviePoster

    private val _reviewList: MutableStateFlow<ReviewStatus> =
        MutableStateFlow(ReviewStatus.Initial)
    val reviewList: StateFlow<ReviewStatus>
        get() = _reviewList



    fun setBasicMovieInfo(movie: BoxOfficeMovie) {
        _basicInfo.value = MovieBasicStatus.Main(movie)
    }


    fun searchReviewMovieList(movieId: Int) {
        viewModelScope.launch {
            _reviewList.value = ReviewStatus.Loading
            remoteRepository.searchReviewInfo(
                movieId = movieId
            ).catch { e ->
                _reviewList.value = ReviewStatus.Failure(e)
            }.collect {
                _reviewList.value = ReviewStatus.Success(it.values.toList())
            }
        }
    }

    fun fetchMovieDetail(movieCd: String?) {
        viewModelScope.launch {
            _movieInfo.value = MovieStatus.Loading

            remoteRepository.searchMovieInfo(
                key = KOBIS_API_KEY,
                movieCd = movieCd
            ).catch { e ->
                _movieInfo.value = MovieStatus.Failure(e)
            }.collect {
                _movieInfo.value = MovieStatus.Success(it)
//                fetchPoster(it.movieInfoResult.movieInfo.movieNmEn)
            }
        }
    }

    fun fetchPoster(title: String) {
        viewModelScope.launch {
            _moviePoster.value = MoviePosterStatus.Loading

            remoteRepository.getMoviePoster(
                key = OMDB_API_KEY,
                title = title
            ).catch { e ->
                _moviePoster.value = MoviePosterStatus.Failure(e)
            }.collect {
                _moviePoster.value = MoviePosterStatus.Success(it.poster)
            }
        }
    }
}
