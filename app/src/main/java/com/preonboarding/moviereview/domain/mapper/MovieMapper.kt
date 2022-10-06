package com.preonboarding.moviereview.domain.mapper

import com.preonboarding.moviereview.data.remote.model.MovieListResponse
import com.preonboarding.moviereview.domain.model.MovieSearchInfo

fun MovieListResponse.mapToMovieSearchInfo(): List<MovieSearchInfo> {
    val searchList = mutableListOf<MovieSearchInfo>()

    this.movieListResult.movieList.map { movie ->
        searchList.add(
            MovieSearchInfo(
                movieCode = movie.movieCode,
                movieKrName = movie.movieKrName,
                movieEnName = movie.movieEnName,
                productionYear = movie.productionYear,
                openDateTime = movie.openDateTime,
                genre = movie.genre
            )
        )
    }

    return searchList
}