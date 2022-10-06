package com.preonboarding.moviereview.domain.repository.remote

import androidx.paging.PagingData
import com.preonboarding.moviereview.domain.model.MovieSearchInfo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovieListByMovieName(key: String, movieName: String): Flow<PagingData<MovieSearchInfo>>
}