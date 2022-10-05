package com.preonboarding.moviereview.domain.repository

import com.preonboarding.moviereview.data.network.model.DailyBoxOffices
import com.preonboarding.moviereview.data.network.model.MovieInfos

interface RemoteRepository {
    suspend fun getDailyBoxOfficeList(key: String, targetDt: String): DailyBoxOffices?
    suspend fun getMoviesInfo(key: String, movieCd: String): MovieInfos?
}