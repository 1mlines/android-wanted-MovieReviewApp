package com.preonboarding.moviereview.data.remote.repository

import com.preonboarding.moviereview.data.api.FireBaseApi
import com.preonboarding.moviereview.data.api.KobisMovieApi
import com.preonboarding.moviereview.data.api.OmdbMovieApi
import com.preonboarding.moviereview.data.remote.model.DailyBoxOfficeResponse
import com.preonboarding.moviereview.data.remote.model.MovieInfoResponse
import com.preonboarding.moviereview.data.remote.model.Review
import com.preonboarding.moviereview.di.RetrofitFireBase
import com.preonboarding.moviereview.di.RetrofitKobis
import com.preonboarding.moviereview.di.RetrofitOmdb
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepositoryImpl @Inject constructor(
    @RetrofitKobis private val kobisMovieApi: KobisMovieApi,
    @RetrofitOmdb private val omdbMovieApi: OmdbMovieApi,
    @RetrofitFireBase private val fireBaseApi: FireBaseApi,
) : RemoteRepository {

    override suspend fun getDailyMovie(
        key: String,
        targetDt: String,
    ): Flow<DailyBoxOfficeResponse> =
        flow {
            emit(kobisMovieApi.searchDailyBoxOfficeList(key = key, targetDt = targetDt))
        }.flowOn(Dispatchers.IO)

    override suspend fun searchMovieInfo(
        key: String,
        movieCd: String,
    ): Flow<MovieInfoResponse> =
        flow {
            emit(kobisMovieApi.searchMovieInfo(key = key, movieCd = movieCd))
        }

    override suspend fun searchReviewInfo(
        movieId: Int,
    ): Flow<Map<String, Review>> =
        flow {
            emit(fireBaseApi.searchReviewInfo(movieId))
        }
}