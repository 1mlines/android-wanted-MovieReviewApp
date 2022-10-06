package com.preonboarding.moviereview.presentation.detailmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    fun getDailyBox(key: String, targetDt: String) {
        viewModelScope.launch {
            val result = dailyBoxOfficeUseCase.getDailyBoxOfficeList(key, targetDt)
            movieCd = result!!.boxOfficeResult!!.dailyBoxOfficeList[0].movieCd
        }
    }

    fun getPosterInfo(title: String, key: String) {
        viewModelScope.launch {
            val result = posterInfoUseCase.getPosterInfo(title, key)
        }
    }

    fun getMovieInfo(key: String, movieCd: String) {
        viewModelScope.launch {
            val result = movieInfosUseCase.getMovieInfo(key, movieCd)
        }
    }
}