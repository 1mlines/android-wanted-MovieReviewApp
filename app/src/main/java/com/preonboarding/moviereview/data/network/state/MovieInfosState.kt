package com.preonboarding.moviereview.data.network.state

import com.preonboarding.moviereview.data.network.model.kobis.MovieInfos

sealed class MovieInfosState{
    data class Success(val movieInfos: MovieInfos): MovieInfosState()
    data class Failure(val throwable: Throwable): MovieInfosState()
}