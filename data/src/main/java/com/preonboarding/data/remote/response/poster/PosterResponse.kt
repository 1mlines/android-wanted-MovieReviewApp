package com.preonboarding.data.remote.response.poster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PosterResponse(
    @SerialName("Plot") val Plot: String = "",
    @SerialName("Poster") val Poster: String = ""
)