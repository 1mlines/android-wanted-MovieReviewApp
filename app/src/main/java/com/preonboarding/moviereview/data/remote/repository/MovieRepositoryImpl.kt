package com.preonboarding.moviereview.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.preonboarding.moviereview.data.paging.MovieSearchResultPagingSource
import com.preonboarding.moviereview.data.remote.model.NetworkResult
import com.preonboarding.moviereview.data.remote.source.MovieDataSource
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
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun getMovieListByMovieName(movieName: String) = Pager(
        config = PagingConfig(
            pageSize = ITEM_PER_PAGE,
            enablePlaceholders = false,
            initialLoadSize = ITEM_PER_PAGE
        ),
        pagingSourceFactory = { MovieSearchResultPagingSource(dataSource, movieName) }
    ).flow

    override fun getMovieInfoByCode(movieCode: String): Flow<NetworkResult<MovieInfo>> = flow {
        val movieInfo = dataSource.getMovieInfoByCode(movieCode).mapToMovieInfo()
        emit(NetworkResult.Success(movieInfo))
    }.flowOn(dispatcher)

    companion object {
        private const val ITEM_PER_PAGE = 10
    }
}