package com.preonboarding.moviereview.data.repository

import com.preonboarding.moviereview.data.network.KobisMovieApi
import com.preonboarding.moviereview.data.network.OmdbMovieApi
import com.preonboarding.moviereview.data.network.state.DailyBoxOfficesState
import com.preonboarding.moviereview.data.network.state.MovieInfosState
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val kobisMovieApi: KobisMovieApi,
    private val omdbMovieApi: OmdbMovieApi
){
    suspend fun getDailyBoxOfficeList(key: String, targetDt: String): DailyBoxOfficesState {
        return runCatching {
            DailyBoxOfficesState.Success(
                dailyBoxOffices = kobisMovieApi.getDailyBoxOfficeList(
                    key = key,
                    targetDt = targetDt
                )
            )
        }.getOrElse {
            DailyBoxOfficesState.Failure(
                throwable = it
            )
        }
    }

    suspend fun getMoviesInfo(key: String, movieCd: String): MovieInfosState {
        return kotlin.runCatching {
            MovieInfosState.Success(
                movieInfos = kobisMovieApi.getMovieInfo(
                    key = key,
                    movieCd = movieCd
                )
            )
        }.getOrElse {
            MovieInfosState.Failure(
                throwable = it
            )
        }
    }
}