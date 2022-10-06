package com.preonboarding.moviereview.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.domain.model.BoxOffice
import com.preonboarding.moviereview.domain.usecase.DailyBoxOfficeUseCase
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import com.preonboarding.moviereview.domain.usecase.PosterInfoUseCase
import com.preonboarding.moviereview.presentation.main.mapper.asModel
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
        getBoxOfficeList(key = "d78dfcf081301ea1719d6d8d36756527", date = date, code = "0105001")
    }

    private fun getBoxOfficeList(key: String, date: String, code: String) {
        viewModelScope.launch {
            val result = dailyBoxOfficeUseCase.getDailyBoxOfficeList(key, date, code)
            if (result?.boxOfficeResult != null) {
                _dailyMovieBoxList.value = result.boxOfficeResult.dailyBoxOfficeList.map {
                    it.asModel()
                }
                result.boxOfficeResult.dailyBoxOfficeList.forEachIndexed { index, data ->
                    launch {
                        val response = movieInfosUseCase.getMovieInfo(
                            key = "d78dfcf081301ea1719d6d8d36756527",
                            movieCd = data.movieCd
                        )
                        if (response != null) {
                            val poster = posterInfoUseCase.getPosterInfo(
                                response.movieInfoResult.movieInfo.movieNmEn,
                                key = "fa8d1585"
                            )

                            _dailyMovieBoxList.value = _dailyMovieBoxList.value.map { boxOffice ->
                                if (boxOffice.movieCd == data.movieCd) {
                                    boxOffice.copy(
                                        moviePoster = poster?.Poster ?: "",
                                        isReady = true
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
