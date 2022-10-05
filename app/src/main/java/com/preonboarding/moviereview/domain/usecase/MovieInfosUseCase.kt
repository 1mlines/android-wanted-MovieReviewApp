package com.preonboarding.moviereview.domain.usecase

import com.preonboarding.moviereview.data.network.model.MovieInfos

interface MovieInfosUseCase {
    suspend fun getMovieInfo(key: String, movieCd: String): MovieInfos?
}