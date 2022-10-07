package com.preonboarding.data.remote.api

import com.preonboarding.data.common.constant.API_KEY_OMDB
import com.preonboarding.data.common.constant.BASE_URL_OMDB
import com.preonboarding.data.common.constant.SEARCH_MOVIE_INFO
import com.preonboarding.data.remote.response.poster.PosterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PosterService {

    @GET(BASE_URL_OMDB)
    suspend fun getPoster(
        @Query("t") movieNmEn: String,
        @Query("y") year: String,
        @Query("apikey") key: String = API_KEY_OMDB,
    ): Response<PosterResponse>
}
