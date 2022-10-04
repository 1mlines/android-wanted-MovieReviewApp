package com.preonboarding.data.remote.api

import com.preonboarding.data.common.constant.SEARCH_MOVIE_INFO
import com.preonboarding.data.remote.response.poster.PosterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PosterService {

    //todo key
    @GET(SEARCH_MOVIE_INFO)
    suspend fun getPoster(
        @Query("t") movieNmEn: String,
    ): Response<PosterResponse>
}