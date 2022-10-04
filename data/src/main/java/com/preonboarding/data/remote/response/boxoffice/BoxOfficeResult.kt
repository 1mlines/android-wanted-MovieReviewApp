package com.preonboarding.data.remote.response.boxoffice

data class BoxOfficeResult(
    val boxofficeType: String,
    val dailyBoxOfficeList: List<DailyBoxOffice>,
    val showRange: String,
)