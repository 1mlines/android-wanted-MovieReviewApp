package com.preonboarding.moviereview.data.repository

import com.preonboarding.moviereview.data.network.model.DailyBoxOfficeResult
import com.preonboarding.moviereview.data.network.model.DailyBoxOffices
import com.preonboarding.moviereview.data.network.model.MovieInfos
import com.preonboarding.moviereview.data.network.state.DailyBoxOfficesState
import com.preonboarding.moviereview.data.network.state.MovieInfosState
import com.preonboarding.moviereview.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): RemoteRepository {
    override suspend fun getDailyBoxOfficeList(
        key: String,
        targetDt: String
    ): DailyBoxOffices? {
        return when(val state = remoteDataSource.getDailyBoxOfficeList(key, targetDt)) {
            is DailyBoxOfficesState.Success -> {
                state.dailyBoxOffices
            }

            is DailyBoxOfficesState.Failure -> {
                null // todo
            }
        }
    }

    override suspend fun getMoviesInfo(key: String, movieCd: String): MovieInfos? {
        return when(val state = remoteDataSource.getMoviesInfo(key, movieCd)) {
            is MovieInfosState.Success -> {
                state.movieInfos
            }
            is MovieInfosState.Failure -> {
                null // todo
            }
        }
    }

}