package com.preonboarding.moviereview.data.repository

import com.preonboarding.moviereview.data.network.model.kobis.DailyBoxOffices
import com.preonboarding.moviereview.data.network.model.kobis.MovieInfos
import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo
import com.preonboarding.moviereview.data.network.state.DailyBoxOfficesState
import com.preonboarding.moviereview.data.network.state.MovieInfosState
import com.preonboarding.moviereview.data.network.state.PosterInfoState
import com.preonboarding.moviereview.domain.model.BoxOffice
import com.preonboarding.moviereview.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RemoteRepository {

    override fun test(key: String, targetDt: String): Flow<List<BoxOffice>> {
        return remoteDataSource.test(key, targetDt)
    }

    override fun test2(key: String, movieCd: String): Flow<MovieInfos> {
        return remoteDataSource.test2(key, movieCd)
    }

    override fun test3(title: String, key: String): Flow<PosterInfo> {
        return remoteDataSource.test3(title, key)
    }

    override suspend fun getDailyBoxOfficeList(
        key: String,
        targetDt: String,
        wideAreaCd : String
    ): DailyBoxOffices? {
        return when(val state = remoteDataSource.getDailyBoxOfficeList(key, targetDt, wideAreaCd)) {
            is DailyBoxOfficesState.Success -> {
                state.dailyBoxOffices
            }

            is DailyBoxOfficesState.Failure -> {
                null
            }
        }
    }

    override suspend fun getMoviesInfo(key: String, movieCd: String): MovieInfos? {
        return when(val state = remoteDataSource.getMoviesInfo(key, movieCd)) {
            is MovieInfosState.Success -> {
                state.movieInfos
            }
            is MovieInfosState.Failure -> {
                null
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