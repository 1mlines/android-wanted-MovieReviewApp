package com.preonboarding.data.remote.response.movieinfo

import kotlinx.serialization.Serializable

@Serializable
data class Staff(
    val peopleNm: String,
    val peopleNmEn: String,
    val staffRoleNm: String
)