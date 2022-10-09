package com.preonboarding.moviereview.domain.repository

import com.preonboarding.moviereview.data.network.model.kobis.DailyBoxOffices
import com.preonboarding.moviereview.data.network.model.kobis.MovieInfos
import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo
import com.preonboarding.moviereview.domain.model.BoxOffice
import com.preonboarding.moviereview.domain.model.ReviewVo
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getDailyBoxOfficeList(key: String, targetDt: String, wideAreaCd: String): DailyBoxOffices?
    suspend fun getMoviesInfo(key: String, movieCd: String): MovieInfos?
    suspend fun getPosterInfo(title: String, key: String): PosterInfo?

}