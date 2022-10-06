package com.preonboarding.moviereview.data.source.remote

import com.preonboarding.moviereview.data.remote.model.MovieListResponse

interface MovieListDataSource {

    suspend fun getMovieList(key: String, movieName: String, page: String): MovieListResponse
}