package com.preonboarding.moviereview.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyBoxOffices(
    val boxOfficeResult: BoxOfficeResult?
)

@Serializable
data class BoxOfficeResult(
    val boxOfficeType: String,
    val showRange: String,
    val dailyBoxOfficeList: List<DailyBoxOffice>
)

@Serializable
data class DailyBoxOffice(
    val rank: String,
    val rankInten: String,
    val rankOldAndNew: String,
    val movieCd: String,
    val movieNm: String,
    val openDt: String,
    val audiCnt: String,
    val audiAcc: String
)

