package com.preonboarding.moviereview.domain.repository.remote

import com.preonboarding.moviereview.data.remote.model.DailyBoxOfficeResponse
import com.preonboarding.moviereview.data.remote.model.MovieInfoResponse
import com.preonboarding.moviereview.data.remote.model.Review
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun searchDailyBoxOfficeList(
        key: String,
        targetDt: String,
    ): Flow<DailyBoxOfficeResponse>

    suspend fun searchMovieInfo(
        key: String,
        movieCd: String,
    ): Flow<MovieInfoResponse>

    suspend fun searchReviewInfo(
        movieId: Int,
    ): Flow<List<Review>>
}