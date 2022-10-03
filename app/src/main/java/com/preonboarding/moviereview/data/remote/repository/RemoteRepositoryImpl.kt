package com.preonboarding.moviereview.data.remote.repository

import com.preonboarding.moviereview.data.api.KobisMovieApi
import com.preonboarding.moviereview.data.api.OmdbMovieApi
import com.preonboarding.moviereview.data.remote.model.DailyBoxOfficeResponse
import com.preonboarding.moviereview.di.RetrofitKobis
import com.preonboarding.moviereview.di.RetrofitOmdb
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepositoryImpl @Inject constructor(
    @RetrofitKobis private val kobisMovieApi: KobisMovieApi,
    @RetrofitOmdb private val omdbMovieApi: OmdbMovieApi
): RemoteRepository {

    override suspend fun searchDailyBoxOfficeList(
        key: String,
        targetDt: String
    ): Flow<DailyBoxOfficeResponse> =
        flow {
            emit(kobisMovieApi.searchDailyBoxOfficeList(key = key, targetDt = targetDt))
        }

}