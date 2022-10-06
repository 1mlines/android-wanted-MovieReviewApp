package com.preonboarding.moviereview.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.presentation.main.mapper.asModel
import com.preonboarding.moviereview.domain.model.BoxOffice
import com.preonboarding.moviereview.domain.usecase.DailyBoxOfficeUseCase
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import com.preonboarding.moviereview.domain.usecase.PosterInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieInfosUseCase: MovieInfosUseCase,
    private val dailyBoxOfficeUseCase: DailyBoxOfficeUseCase,
    private val posterInfoUseCase: PosterInfoUseCase
) : ViewModel() {

    private val _dailyMovieBoxList = MutableStateFlow(emptyList<BoxOffice>())
    val dailyMovieBoxList = _dailyMovieBoxList.asStateFlow()

    init {
        val now = LocalDate.now()
        val nowDate = now.minusDays(1)
        // 날짜, 시간을 가져오고 싶은 형태 선언
        val date = nowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        getBoxOfficeList(key = "d78dfcf081301ea1719d6d8d36756527", date = date)
    }

    private fun getBoxOfficeList(key: String, date: String) {
        viewModelScope.launch {
            val result = dailyBoxOfficeUseCase.getDailyBoxOfficeList(key, date)
            if (result?.boxOfficeResult != null) {
                _dailyMovieBoxList.value = result.boxOfficeResult.dailyBoxOfficeList.map {
                    it.asModel()
                }

                _dailyMovieBoxList.value.forEachIndexed { index, boxOffice ->
                    launch {
                        val response = movieInfosUseCase.getMovieInfo(
                            "d78dfcf081301ea1719d6d8d36756527",
                            boxOffice.movieCd
                        )
                        if (response != null) {
                            val poster = posterInfoUseCase.getPosterInfo(
                                response.movieInfoResult.movieInfo.movieNmEn,
                                key = "fa8d1585"
                            )
                            if (poster?.Poster != null) {
                                _dailyMovieBoxList.value =
                                    _dailyMovieBoxList.value.mapIndexed { mapIndex, boxOffice ->
                                        if (mapIndex == index) {
                                            boxOffice.copy(
                                                moviePoster = poster.Poster
                                            )
                                        } else {
                                            boxOffice
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
    }
}
