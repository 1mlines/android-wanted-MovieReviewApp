package com.preonboarding.moviereview.data.source.remote

import com.preonboarding.moviereview.data.api.KobisMovieApi
import com.preonboarding.moviereview.data.remote.model.MovieListResponse
import com.preonboarding.moviereview.di.RetrofitKobis
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieListDataSourceImpl @Inject constructor(
    @RetrofitKobis private val api: KobisMovieApi
) : MovieListDataSource {

    override suspend fun getMovieList(key: String, movieName: String, page: String): MovieListResponse {
        return api.getMovieList(key, movieName, page)
    }
}