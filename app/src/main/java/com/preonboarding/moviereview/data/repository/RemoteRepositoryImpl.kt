package com.preonboarding.moviereview.data.repository

import com.preonboarding.moviereview.data.network.model.kobis.DailyBoxOffices
import com.preonboarding.moviereview.data.network.model.kobis.MovieInfos
import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo
import com.preonboarding.moviereview.data.network.state.DailyBoxOfficesState
import com.preonboarding.moviereview.data.network.state.MovieInfosState
import com.preonboarding.moviereview.data.network.state.PosterInfoState
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

    override suspend fun getPosterInfo(title: String, key: String): PosterInfo? {
        return when(val state = remoteDataSource.getPosterInfo(title, key)) {
            is PosterInfoState.Success -> {
                state.posterInfo
            }
            is PosterInfoState.Failure -> {
                null
            }
        }
    }
}