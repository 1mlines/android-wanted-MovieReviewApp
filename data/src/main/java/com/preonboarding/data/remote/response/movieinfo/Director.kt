package com.preonboarding.data.remote.response.movieinfo

import kotlinx.serialization.Serializable

@Serializable
data class Director(
    val peopleNm: String,
    val peopleNmEn: String
)