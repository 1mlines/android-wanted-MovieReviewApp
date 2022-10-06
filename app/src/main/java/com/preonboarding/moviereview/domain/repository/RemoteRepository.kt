package com.preonboarding.moviereview.domain.repository

import com.preonboarding.moviereview.data.network.model.kobis.DailyBoxOffices
import com.preonboarding.moviereview.data.network.model.kobis.MovieInfos
import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo
import com.preonboarding.moviereview.domain.model.BoxOffice
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getDailyBoxOfficeList(key: String, targetDt: String, wideAreaCd: String): DailyBoxOffices?
    suspend fun getMoviesInfo(key: String, movieCd: String): MovieInfos?
    suspend fun getPosterInfo(title: String, key: String): PosterInfo?

    fun test(key: String, targetDt: String): Flow<List<BoxOffice>>
    fun test2(key: String, movieCd: String): Flow<MovieInfos>
    fun test3(title: String, key: String): Flow<PosterInfo>
}