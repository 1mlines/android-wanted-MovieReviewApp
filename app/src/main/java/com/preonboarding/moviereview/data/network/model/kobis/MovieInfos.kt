package com.preonboarding.moviereview.data.network.model.kobis

import kotlinx.serialization.Serializable

@Serializable
data class MovieInfos(
    val movieInfoResult: MovieInfoResult
)

@Serializable
data class MovieInfoResult(
    val movieInfo: MovieInfo
)

@Serializable
data class MovieInfo (
    val movieCd: String,
    val movieNm: String,
    val prdtYear: String,
    val showTm: String,
    val openDt: String,
    val genres: Genres,
    val directors: Directors,
    val actors: Actors,
    val watchGradeNm: String
)

@Serializable
data class Genres(
    val genre: List<Genre>
)

@Serializable
data class Genre(
    val genreNm: String
)

@Serializable
data class Directors(
    val director: List<Director>
)

@Serializable
data class Director(
    val peopleNm: String
)

@Serializable
data class Actors(
    val actor: List<Actor>
)

@Serializable
data class Actor(
    val peopleNm: String,
    val cast: String
)