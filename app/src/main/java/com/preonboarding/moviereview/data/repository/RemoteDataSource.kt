package com.preonboarding.moviereview.data.repository

import android.util.Log
import com.preonboarding.moviereview.data.mapper.asModel
import com.preonboarding.moviereview.data.network.KobisMovieApi
import com.preonboarding.moviereview.data.network.OmdbMovieApi
import com.preonboarding.moviereview.data.network.model.kobis.MovieInfos
import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo
import com.preonboarding.moviereview.data.network.state.DailyBoxOfficesState
import com.preonboarding.moviereview.data.network.state.MovieInfosState
import com.preonboarding.moviereview.data.network.state.PosterInfoState
import com.preonboarding.moviereview.domain.model.BoxOffice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val kobisMovieApi: KobisMovieApi,
    private val omdbMovieApi: OmdbMovieApi
){
    suspend fun getDailyBoxOfficeList(key: String, targetDt: String, wideAreaCd: String): DailyBoxOfficesState {
        return runCatching {
            DailyBoxOfficesState.Success(
                dailyBoxOffices = kobisMovieApi.getDailyBoxOfficeList(
                    key = key,
                    targetDt = targetDt,
                    wideAreaCd = wideAreaCd
                )
            )
        }.getOrElse {
            DailyBoxOfficesState.Failure(
                throwable = it
            )
        }
    }

    suspend fun getMoviesInfo(key: String, movieCd: String): MovieInfosState {
        return runCatching {
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

    suspend fun getPosterInfo(title: String, key: String): PosterInfoState {
        return runCatching {
            PosterInfoState.Success(
                posterInfo = omdbMovieApi.getMoviePoster(
                    title = title,
                    key = key
                )
            )
        }.getOrElse {
            Log.d("poster fail", PosterInfoState.Failure(it).toString())
            PosterInfoState.Failure(
                throwable = it
            )
        }
    }
}
