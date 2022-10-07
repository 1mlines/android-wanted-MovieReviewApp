package com.preonboarding.moviereview.presentation.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.data.network.model.kobis.MovieInfo
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieInfosUseCase: MovieInfosUseCase
) : ViewModel() {
    private var _movieInfo = MutableLiveData<MovieInfo>()
    val movieInfo: LiveData<MovieInfo> = _movieInfo

    fun getMovieInfo(movieCd: String, key: String) {
        viewModelScope.launch {
            var movieInfos = movieInfosUseCase.getMovieInfo(key, movieCd)
            if (movieInfos != null) {
                _movieInfo.value = movieInfos.movieInfoResult.movieInfo
            }
        }
    }
}