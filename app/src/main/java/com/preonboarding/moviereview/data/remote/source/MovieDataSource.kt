package com.preonboarding.moviereview.data.remote.source

import com.preonboarding.moviereview.data.remote.model.MovieInfoResponse
import com.preonboarding.moviereview.data.remote.model.MovieListResponse
import com.preonboarding.moviereview.data.remote.model.MoviePosterResponse

interface MovieDataSource {

    suspend fun getMovieList(movieName: String, page: String): MovieListResponse

    suspend fun getMovieInfoByCode(movieCode: String): MovieInfoResponse

    suspend fun getMoviePosterByMovieName(movieName: String): MoviePosterResponse
}