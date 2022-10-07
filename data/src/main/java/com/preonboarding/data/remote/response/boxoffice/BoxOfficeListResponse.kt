package com.preonboarding.data.remote.response.boxoffice

import kotlinx.serialization.Serializable

@Serializable
data class BoxOfficeListResponse(
    val boxOfficeResult: BoxOfficeResult,
)
