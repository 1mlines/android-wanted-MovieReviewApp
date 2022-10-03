package com.preonboarding.moviereview.data.api

import retrofit2.http.GET

interface OmdbMovieApi {
    // custom
    // get by title or id
    @GET()
    suspend fun getMovie(apiKey: String, title: String)

    // search
    @GET()
    suspend fun search(s: String)
}