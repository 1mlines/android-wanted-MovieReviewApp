package com.preonboarding.moviereview.domain.repository.remote

import androidx.paging.PagingData
import com.preonboarding.moviereview.data.remote.model.NetworkState
import com.preonboarding.moviereview.domain.model.MovieInfo
import com.preonboarding.moviereview.domain.model.MoviePoster
import com.preonboarding.moviereview.domain.model.MovieSearchInfo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieListByMovieName(movieName: String): Flow<PagingData<MovieSearchInfo>>

    fun getMovieInfoByCode(movieCode: String): Flow<NetworkState<MovieInfo>>

    fun getMoviePosterByMovieName(movieName: String): Flow<NetworkState<MoviePoster>>
}