package com.preonboarding.moviereview.data.network

import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbMovieApi {
    @GET("http://www.omdbapi.com/")
    suspend fun getMoviePoster(
        @Query("apikey") key: String,
        @Query("t") title: String
    ): PosterInfo
}