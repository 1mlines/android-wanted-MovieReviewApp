package com.preonboarding.moviereview.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.preonboarding.moviereview.data.paging.MovieSearchResultPagingSource
import com.preonboarding.moviereview.data.remote.model.NetworkState
import com.preonboarding.moviereview.data.remote.source.MovieDataSource
import com.preonboarding.moviereview.di.DefaultDispatcher
import com.preonboarding.moviereview.di.IoDispatcher
import com.preonboarding.moviereview.domain.mapper.mapToMovieInfo
import com.preonboarding.moviereview.domain.model.MovieInfo
import com.preonboarding.moviereview.domain.repository.remote.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun getMovieListByMovieName(movieName: String) = Pager(
        config = PagingConfig(
            pageSize = ITEM_PER_PAGE,
            enablePlaceholders = false,
            initialLoadSize = ITEM_PER_PAGE
        ),
        pagingSourceFactory = { MovieSearchResultPagingSource(dataSource, movieName) }
    ).flow.flowOn(defaultDispatcher)

    override fun getMovieInfoByCode(movieCode: String): Flow<NetworkState<MovieInfo>> = flow {
        val movieInfo = dataSource.getMovieInfoByCode(movieCode).mapToMovieInfo()
        emit(NetworkState.Success(movieInfo))
    }.flowOn(ioDispatcher)

    companion object {
        private const val ITEM_PER_PAGE = 10
    }
}