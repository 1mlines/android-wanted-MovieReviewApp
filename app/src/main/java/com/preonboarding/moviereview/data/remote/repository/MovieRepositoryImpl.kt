package com.preonboarding.moviereview.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.preonboarding.moviereview.data.paging.MovieSearchResultPagingSource
import com.preonboarding.moviereview.data.source.remote.MovieListDataSource
import com.preonboarding.moviereview.domain.model.MovieSearchInfo
import com.preonboarding.moviereview.domain.repository.remote.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieListDataSource
) : MovieRepository {

    override suspend fun getMovieListByMovieName(
        key: String,
        movieName: String
    ): Flow<PagingData<MovieSearchInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = ITEM_PER_PAGE,
                enablePlaceholders = false,
                initialLoadSize = ITEM_PER_PAGE
            ),
            pagingSourceFactory = { MovieSearchResultPagingSource(dataSource, key, movieName)}
        ).flow
    }

    companion object {
        private const val ITEM_PER_PAGE = 10
    }
}