package com.preonboarding.moviereview.data.api

import com.preonboarding.moviereview.data.remote.model.DailyBoxOfficeResponse
import com.preonboarding.moviereview.data.remote.model.MoviePosterResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface OmdbMovieApi {
    @GET()
    suspend fun getMoviePoster(
        @Query("apikey") apikey: String,
        @Query("t") title: String
    ): MoviePosterResponse
}