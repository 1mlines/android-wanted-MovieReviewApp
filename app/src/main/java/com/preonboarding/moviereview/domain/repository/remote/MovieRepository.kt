package com.preonboarding.moviereview.domain.repository.remote

import androidx.paging.PagingData
import com.preonboarding.moviereview.data.remote.model.NetworkResult
import com.preonboarding.moviereview.domain.model.MovieInfo
import com.preonboarding.moviereview.domain.model.MovieSearchInfo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieListByMovieName(movieName: String): Flow<PagingData<MovieSearchInfo>>

    fun getMovieInfoByCode(movieCode: String): Flow<NetworkResult<MovieInfo>>
}