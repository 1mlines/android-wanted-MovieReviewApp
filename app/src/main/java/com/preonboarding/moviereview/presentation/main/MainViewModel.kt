package com.preonboarding.moviereview.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.data.mapper.asModel
import com.preonboarding.moviereview.domain.model.BoxOffice
import com.preonboarding.moviereview.domain.repository.RemoteRepository
import com.preonboarding.moviereview.domain.usecase.DailyBoxOfficeUseCase
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import com.preonboarding.moviereview.domain.usecase.PosterInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieInfosUseCase: MovieInfosUseCase,
    private val dailyBoxOfficeUseCase: DailyBoxOfficeUseCase,
    private val posterInfoUseCase: PosterInfoUseCase,
    private val repository: RemoteRepository
) : ViewModel() {
    var movieCd = ""
    fun getDailyBox(id: String, targetDt: String, wideAreaCd: String){
        viewModelScope.launch {
            val result = dailyBoxOfficeUseCase.getDailyBoxOfficeList(id, targetDt, wideAreaCd)
            movieCd = result!!.boxOfficeResult!!.dailyBoxOfficeList[0].movieCd
        }
    }

    private fun test2() {
        viewModelScope.launch {
            _dailyMovieBoxList.value.forEachIndexed { index, item ->
                repository.test2("d78dfcf081301ea1719d6d8d36756527", item.movieCd).collect { info ->
                    val newList = repository.test3(info.movieInfoResult.movieInfo.movieNmEn, "fa8d1585").zip(
                        _dailyMovieBoxList
                    ) { poster, list ->
                        if (poster.Poster != null) {
                            list[index].copy(
                                moviePoster = poster.Poster
                            )
                        }

//                        list.map { boxOffice ->
//                            if (poster.Poster != null) {
//                                boxOffice.copy(
//                                    moviePoster = poster.Poster
//                                )
//                            }
//                        }
                        list
                    }
                    newList.collect {
                        _dailyMovieBoxList.value = it
                    }
                }
            }
        }
    }
//    fun getDailyBox(id: String, targetDt: String) {
//        viewModelScope.launch {
//            val result = dailyBoxOfficeUseCase.getDailyBoxOfficeList(id, targetDt)
//            movieCd = result!!.boxOfficeResult!!.dailyBoxOfficeList[0].movieCd
//        }
//    }
//
//    fun getPosterInfo(title: String, key: String) {
//        viewModelScope.launch {
//            val result = posterInfoUseCase.getPosterInfo(title, key)
//        }
//    }
}
