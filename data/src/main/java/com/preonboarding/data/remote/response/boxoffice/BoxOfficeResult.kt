package com.preonboarding.data.remote.response.boxoffice

import kotlinx.serialization.Serializable

@Serializable
data class BoxOfficeResult(
    val boxofficeType: String,
    val dailyBoxOfficeList: List<DailyBoxOffice>,
    val showRange: String,
)