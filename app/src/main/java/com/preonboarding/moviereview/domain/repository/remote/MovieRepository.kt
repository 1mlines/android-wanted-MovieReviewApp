package com.preonboarding.moviereview.domain.repository.remote

import androidx.paging.PagingData
import com.preonboarding.moviereview.domain.model.MovieSearchInfo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieListByMovieName(movieName: String): Flow<PagingData<MovieSearchInfo>>
}