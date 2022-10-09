package com.preonboarding.data.remote.response.movieinfo

import kotlinx.serialization.Serializable

@Serializable
data class ShowType(
    val showTypeGroupNm: String,
    val showTypeNm: String
)