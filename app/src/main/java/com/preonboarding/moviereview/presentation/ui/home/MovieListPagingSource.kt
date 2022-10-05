package com.preonboarding.moviereview.presentation.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.preonboarding.moviereview.data.api.KobisMovieApi
import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie
import retrofit2.HttpException
import java.io.IOException

class MovieListPagingSource(
    private val kobisMovieApi: KobisMovieApi,
    private val key: String,
    private val targetDt: String
) : PagingSource<Int, BoxOfficeMovie>() {

    override fun getRefreshKey(state: PagingState<Int, BoxOfficeMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BoxOfficeMovie> {
        val page = params.key ?: PAGING_START_PAGE

        return try {
            val response = kobisMovieApi.searchDailyBoxOfficeList(key, targetDt)
            val boxOfficeResult = response.boxOfficeResult
            val boxOfficeMovie = boxOfficeResult.dailyBoxOfficeList

            LoadResult.Page(
                data = boxOfficeMovie,
                prevKey = if (page == PAGING_START_PAGE) null else page - 1,
                nextKey = if (boxOfficeMovie.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)

        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}

// 이동 예정
const val PAGING_START_PAGE=1
