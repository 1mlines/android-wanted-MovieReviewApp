package com.preonboarding.moviereview.presentation.ui.home.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.preonboarding.moviereview.data.api.KobisMovieApi
import com.preonboarding.moviereview.presentation.ui.home.MovieListPagingSource
import javax.inject.Inject

class Remote @Inject constructor(
    val api: KobisMovieApi
) {
    fun getMovieList(key: String, targetDt: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ), pagingSourceFactory = {MovieListPagingSource(api, key, targetDt)}
        ).flow
}