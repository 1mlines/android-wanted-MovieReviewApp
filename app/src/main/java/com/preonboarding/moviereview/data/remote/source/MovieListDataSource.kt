package com.preonboarding.moviereview.data.remote.source

import com.preonboarding.moviereview.data.remote.model.MovieListResponse

interface MovieListDataSource {

    suspend fun getMovieList(movieName: String, page: String): MovieListResponse
}