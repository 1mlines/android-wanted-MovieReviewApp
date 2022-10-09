package com.preonboarding.moviereview.domain.usecase

import com.preonboarding.moviereview.data.network.model.kobis.MovieInfos

interface MovieInfosUseCase {
    suspend fun getMovieInfo(key: String, movieCd: String): MovieInfos?
}