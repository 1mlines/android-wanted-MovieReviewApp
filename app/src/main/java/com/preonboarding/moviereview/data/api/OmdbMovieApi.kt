package com.preonboarding.moviereview.data.api

import com.preonboarding.moviereview.data.remote.model.MoviePosterResponse
import com.preonboarding.moviereview.presentation.common.const.OMDB_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface OmdbMovieApi {
    @GET(".")
    suspend fun getMoviePoster(
        @Query("apikey") apikey: String = OMDB_API_KEY,
        @Query("t") title: String
    ): MoviePosterResponse
}
