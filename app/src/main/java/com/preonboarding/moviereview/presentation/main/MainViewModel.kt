package com.preonboarding.moviereview.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.domain.usecase.DailyBoxOfficeUseCase
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import com.preonboarding.moviereview.domain.usecase.PosterInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieInfosUseCase: MovieInfosUseCase,
    private val dailyBoxOfficeUseCase: DailyBoxOfficeUseCase,
    private val posterInfoUseCase: PosterInfoUseCase
) : ViewModel() {
    var movieCd = ""
    fun getDailyBox(id: String, targetDt: String){
        viewModelScope.launch {
            val result = dailyBoxOfficeUseCase.getDailyBoxOfficeList(id, targetDt)
            movieCd = result!!.boxOfficeResult!!.dailyBoxOfficeList[0].movieCd
        }
    }

    fun getPosterInfo(title: String, key: String) {
        viewModelScope.launch {
            val result = posterInfoUseCase.getPosterInfo(title, key)
        }
    }
}