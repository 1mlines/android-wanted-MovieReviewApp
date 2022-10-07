package com.preonboarding.moviereview.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.preonboarding.moviereview.data.paging.MovieSearchResultPagingSource
import com.preonboarding.moviereview.data.remote.source.MovieListDataSource
import com.preonboarding.moviereview.domain.repository.remote.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieListDataSource
) : MovieRepository {

    override fun getMovieListByMovieName(movieName: String) = Pager(
        config = PagingConfig(
            pageSize = ITEM_PER_PAGE,
            enablePlaceholders = false,
            initialLoadSize = ITEM_PER_PAGE
        ),
        pagingSourceFactory = { MovieSearchResultPagingSource(dataSource, movieName) }
    ).flow


    companion object {
        private const val ITEM_PER_PAGE = 10
    }
}