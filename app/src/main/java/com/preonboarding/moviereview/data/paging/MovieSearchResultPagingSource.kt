package com.preonboarding.moviereview.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.preonboarding.moviereview.data.source.remote.MovieListDataSource
import com.preonboarding.moviereview.domain.mapper.mapToMovieSearchInfo
import com.preonboarding.moviereview.domain.model.MovieSearchInfo
import kotlinx.coroutines.delay

class MovieSearchResultPagingSource(
    private val dataSource: MovieListDataSource,
    private val movieName: String
) : PagingSource<Int, MovieSearchInfo>() {
    override fun getRefreshKey(state: PagingState<Int, MovieSearchInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearchInfo> {
        val page = params.key ?: STARTING_PAGE

        if (page != STARTING_PAGE) delay(100L)

        return try {
            val searchResult =
                dataSource.getMovieList(movieName = movieName, page = page.toString())
                    .mapToMovieSearchInfo()

            LoadResult.Page(
                data = searchResult,
                prevKey = if (page == STARTING_PAGE) null else page - 1,
                nextKey = if (searchResult.isEmpty()) null else page + 1
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }


    companion object {
        private const val STARTING_PAGE = 1
    }
}