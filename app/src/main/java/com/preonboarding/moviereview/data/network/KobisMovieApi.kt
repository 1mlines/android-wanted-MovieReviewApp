package com.preonboarding.moviereview.data.network

import com.preonboarding.moviereview.data.network.model.DailyBoxOfficeResponse
import com.preonboarding.moviereview.data.network.model.MovieInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisMovieApi {
    @GET("boxoffice/searchDailyBoxOfficeList.json")
    suspend fun getDailyBoxOfficeList(
        @Query("key") key: String,
        @Query("targetDt") targetDt: String
    ): DailyBoxOfficeResponse

    @GET("movie/searchMovieInfo.json")
    suspend fun getMovieInfo(
        @Query("key") key: String,
        @Query("movieCd") movieCd: String
    ): MovieInfoResponse
}