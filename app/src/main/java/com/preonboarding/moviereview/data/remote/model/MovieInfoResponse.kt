package com.preonboarding.moviereview.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieInfoResponse(
    val movieInfoResult: MovieInfoResult
)

@Serializable
data class MovieInfoResult(
    val movieInfo: MovieInfo,
    val source: String,
)

@Serializable
data class MovieInfo(
    val movieCd: String,
    val movieNm: String,
    val movieNmEn: String,
    val movieNmOg: String,
    val showTm: String,
    val prdtYear: String,
    val openDt: String,
    val prdtStatNm: String,
    val typeNm: String,
    val nations: List<Nations>,
    val genres: List<Genres>,
    val directors: List<Directors>,
    val actors: List<Actors>,
    val showTypes: List<ShowTypes>,
    val companys: List<Companys>,
    val audits: List<Audits>,
    val staffs: List<Staffs>,
    val source: String
)

@Serializable
data class Nations(
    val nationNm: String
)

@Serializable
data class Genres(
    val genreNm: String,
)

@Serializable
data class Directors(
    val peopleNm: String,
    val peopleNmEn: String
)

@Serializable
data class Actors(
    val peopleNm: String,
    val peopleNmEn: String,
    val cast: String,
    val castEn: String
)

@Serializable
data class ShowTypes(
    val showTypeGroupNm: String,
    val showTypeNm: String
)

@Serializable
data class Companys(
    val companyCd: String,
    val companyNm: String,
    val companyNmEn: String,
    val companyPartNm: String
)

@Serializable
data class Audits(
    val auditNo: String,
    val watchGradeNm: String
)

@Serializable
data class Staffs(
    val peopleNm: String,
    val peopleNmEn: String,
    val staffRoleNm: String
)
