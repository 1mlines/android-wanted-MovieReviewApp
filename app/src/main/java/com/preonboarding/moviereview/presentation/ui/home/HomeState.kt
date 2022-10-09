package com.preonboarding.moviereview.presentation.ui.home

import com.preonboarding.moviereview.data.remote.model.DailyBoxOfficeResponse

sealed class HomeState {

    object Loading : HomeState()

    class Failure(val msg:Throwable) : HomeState()

    class Success(val data: DailyBoxOfficeResponse) : HomeState()

    object Empty : HomeState()
}