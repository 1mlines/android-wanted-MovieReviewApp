package com.preonboarding.data.remote.api

import com.preonboarding.data.common.constant.SEARCH_MOVIE_INFO
import com.preonboarding.data.remote.response.movieinfo.MovieInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInfoService {

    //todo key
    @GET(SEARCH_MOVIE_INFO)
    suspend fun getMovieInfo(
        @Query("movieCd") movieCd: String,
    ): Response<MovieInfoResponse>
}