package com.preonboarding.moviereview.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyBoxOfficeResponse(
    val boxOfficeResult: BoxOfficeResult
)

@Serializable
data class BoxOfficeResult(
    val boxOfficeType: String,
    val showRange: String,
    val dailyBoxOfficeList: List<BoxOfficeMovie>
)

@Serializable
data class BoxOfficeMovie(
    val rnum: String,
    val rank: String,
    val rankInten: String,
    val rankOldAndNew: String,
    val movieCd: String,
    val movieNm: String,
    val openDt: String,
    val salesAmt: String,
    val salesShare: String,
    val salesInten: String,
    val salesChange: String,
    val salesAcc: String,
    val audiCnt: String,
    val audiInten: String,
    val audiChange: String,
    val audiAcc: String,
    val scrnCnt: String,
    val showCnt: String
    )