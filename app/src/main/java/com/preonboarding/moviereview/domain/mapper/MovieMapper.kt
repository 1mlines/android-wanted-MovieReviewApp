package com.preonboarding.moviereview.domain.mapper

import com.preonboarding.moviereview.data.remote.model.MovieInfoResponse
import com.preonboarding.moviereview.data.remote.model.MovieListResponse
import com.preonboarding.moviereview.data.remote.model.MoviePosterResponse
import com.preonboarding.moviereview.domain.model.MovieInfo
import com.preonboarding.moviereview.domain.model.MoviePoster
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

fun MovieInfoResponse.mapToMovieInfo(): MovieInfo {
    return MovieInfo(
        movieCode = this.movieInfoResult.movieInfo.movieCd,
        movieKrName = this.movieInfoResult.movieInfo.movieNm,
        movieEnName = this.movieInfoResult.movieInfo.movieNmEn,
        movieRunTime = this.movieInfoResult.movieInfo.showTm,
        productionYear = this.movieInfoResult.movieInfo.prdtYear,
        openDateTime = this.movieInfoResult.movieInfo.openDt,
        genres = this.movieInfoResult.movieInfo.genres?.map { genres ->
            MovieInfo.Genres(
                genreName = genres.genreNm
            )
        } ?: emptyList(),
        directors = this.movieInfoResult.movieInfo.directors?.map { directors ->
            MovieInfo.Directors(
                director = directors.peopleNm
            )
        } ?: emptyList(),
        actors = this.movieInfoResult.movieInfo.actors?.map { actors ->
            MovieInfo.Actors(
                actor = actors.peopleNm,
                castRole = actors.cast
            )
        } ?: emptyList(),
        audits = this.movieInfoResult.movieInfo.audits[0].watchGradeNm
    )
}

fun MoviePosterResponse.mapToMoviePoster(): MoviePoster {
    return MoviePoster(
        movieTitle = this.title,
        movieImageUrl = this.poster
    )
}