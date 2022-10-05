package com.preonboarding.moviereview.data.network.state

import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo

sealed class PosterInfoState {
    data class Success(val posterInfo: PosterInfo): PosterInfoState()
    data class Failure(val throwable: Throwable): PosterInfoState()
}