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
//        val prdtYear: String,
//        val showTm: String,
//        val openDt: String,
//        val prdtStatNm: String,
//        val typeNm: String,
//        val nations: String,
//        val nationNm: String,
//        val genreNm: String,
//        val peopleNm: String,
//        val peopleNmEn: String,
//        val actors: String,
//        // val peopleNm: String,
//        // val peopleNmEn: String,
//        val cast: String,
//        val castEn: String,
//        val showTypes: String,
//        val showTypeGroupNm: String,
//        val showTypeNm: String,
//        val audits: String,
//        val auditNo: String,
//        val watchGradeNm: String,
//        val companys: String,
//        val companyCd: String,
//        val companyNm: String,
//        val companyNmEn: String,
//        val companyPartNm: String,
//        val staffs: String,
//        // val peopleNm: String,
//        // val peopleNmEn: String,
//        val staffRoleNm: String,
)