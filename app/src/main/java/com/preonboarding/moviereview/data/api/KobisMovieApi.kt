package com.preonboarding.moviereview.data.api

import com.preonboarding.moviereview.data.remote.model.DailyBoxOfficeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisMovieApi {

    // 일별 박스오피스
    @GET("boxoffice/searchDailyBoxOfficeList.json")
    suspend fun searchDailyBoxOfficeList(
        @Query("key") key: String,
        @Query("targetDt") targetDt: String
    ): DailyBoxOfficeResponse
}