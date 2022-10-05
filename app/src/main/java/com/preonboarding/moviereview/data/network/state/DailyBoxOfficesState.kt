package com.preonboarding.moviereview.data.network.state

import com.preonboarding.moviereview.data.network.model.DailyBoxOffices

sealed class DailyBoxOfficesState {
    data class Success(val dailyBoxOffices: DailyBoxOffices) : DailyBoxOfficesState()
    data class Failure(val throwable: Throwable) : DailyBoxOfficesState()
}