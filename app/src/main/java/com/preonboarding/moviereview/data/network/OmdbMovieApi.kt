package com.preonboarding.moviereview.data.network

import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbMovieApi {
    @GET("?")
    suspend fun getMoviePoster(
        @Query("t") title: String,
        @Query("apikey") key: String
    ): PosterInfo
}