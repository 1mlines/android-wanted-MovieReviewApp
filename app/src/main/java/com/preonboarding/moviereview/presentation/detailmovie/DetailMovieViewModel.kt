package com.preonboarding.moviereview.presentation.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.data.network.model.kobis.MovieInfo
import com.preonboarding.moviereview.data.network.model.kobis.MovieInfos
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

    private var _result = MutableLiveData<MovieInfo>()
    val result: LiveData<MovieInfo> = _result

    private var _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl

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
            var movieInfos = movieInfosUseCase.getMovieInfo(key, movieCd)
            if (movieInfos != null){
                _result.value = movieInfos.movieInfoResult.movieInfo
            }
        }
    }
}