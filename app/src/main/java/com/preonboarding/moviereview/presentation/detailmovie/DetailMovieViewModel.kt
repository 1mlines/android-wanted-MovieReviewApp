package com.preonboarding.moviereview.presentation.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.data.network.model.kobis.MovieInfo
import com.preonboarding.moviereview.domain.usecase.DailyBoxOfficeUseCase
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import com.preonboarding.moviereview.domain.usecase.PosterInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieInfosUseCase: MovieInfosUseCase,
    private val dailyBoxOfficeUseCase: DailyBoxOfficeUseCase,
    private val posterInfoUseCase: PosterInfoUseCase
) : ViewModel() {
    var movieCd = ""

    private var _movieInfo = MutableLiveData<MovieInfo>()
    val movieInfo: LiveData<MovieInfo> = _movieInfo

    private var _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl

    fun getPosterInfo(movieNmEn: String, key: String) {
        viewModelScope.launch {
            val posterInfo = posterInfoUseCase.getPosterInfo(movieNmEn, key)
            if (posterInfo != null) {
                _imageUrl.value = posterInfo.Poster
            }
        }
    }

    fun getMovieInfo(movieCd: String, key: String) {
        viewModelScope.launch {
            var movieInfos = movieInfosUseCase.getMovieInfo(key, movieCd)
            if (movieInfos != null) {
                _movieInfo.value = movieInfos.movieInfoResult.movieInfo
            }
        }
    }
}