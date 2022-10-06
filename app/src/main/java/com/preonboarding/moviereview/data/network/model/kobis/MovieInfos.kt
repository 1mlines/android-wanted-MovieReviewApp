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
    val movieNmEn: String,
    val movieNmOg: String,
    val prdtYear: String,
    val showTm: String,
    val openDt: String,
    val genres: List<Genre>,
    val directors: List<Director>,
    val actors: List<Actor>,
    val audits: List<Audit>
)

@Serializable
data class Genres(
    val genres: List<Genre>
)

@Serializable
data class Audit(
    val auditNo: String,
    val watchGradeNm: String
)

@Serializable
data class Genre(
    val genreNm: String
)

@Serializable
data class Directors(
    val directors: List<Director>
)

@Serializable
data class Director(
    val peopleNm: String
)

@Serializable
data class Actors(
    val actors: List<Actor>
)

@Serializable
data class Actor(
    val peopleNm: String,
    val cast: String
)