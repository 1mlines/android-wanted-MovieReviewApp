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
    val movieCd: String, //고유번호
    val movieNm: String, //영화명
    val prdtYear: String, //제작연도
    val showTm: String, //상영시간
    val openDt: String, //개봉일? 개봉연도?
    val genreNm: String, //장르
    val directors: Directors, //감독진
    val actors: Actors, //출연진
    val watchGradeNm: String //관람등급
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