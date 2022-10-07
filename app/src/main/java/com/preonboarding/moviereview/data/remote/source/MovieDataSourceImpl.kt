package com.preonboarding.moviereview.data.remote.source

import com.preonboarding.moviereview.data.api.KobisMovieApi
import com.preonboarding.moviereview.data.api.OmdbMovieApi
import com.preonboarding.moviereview.data.remote.model.MovieInfoResponse
import com.preonboarding.moviereview.data.remote.model.MovieListResponse
import com.preonboarding.moviereview.data.remote.model.MoviePosterResponse
import com.preonboarding.moviereview.di.RetrofitKobis
import com.preonboarding.moviereview.di.RetrofitOmdb
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDataSourceImpl @Inject constructor(
    @RetrofitKobis private val kobisApi: KobisMovieApi,
    @RetrofitOmdb private val OmdbApi: OmdbMovieApi
) : MovieDataSource {

    override suspend fun getMovieList(movieName: String, page: String): MovieListResponse {
        return kobisApi.getMovieList(movieKrName = movieName, page = page)
    }

    override suspend fun getMovieInfoByCode(movieCode: String): MovieInfoResponse {
        return kobisApi.searchMovieInfo(movieCd = movieCode)
    }

    override suspend fun getMoviePosterByMovieName(movieName: String): MoviePosterResponse {
        return OmdbApi.getMoviePoster(title = movieName)
    }
}