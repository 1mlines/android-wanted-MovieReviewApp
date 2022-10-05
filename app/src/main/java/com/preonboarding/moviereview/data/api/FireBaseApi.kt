package com.preonboarding.moviereview.data.api

import com.preonboarding.moviereview.data.remote.model.Review
import retrofit2.http.GET
import retrofit2.http.Path

interface FireBaseApi {

    @GET("{movieId}.json")
    suspend fun searchReviewInfo(
        @Path("movieId") movieId: Int,
    ): List<Review>
}