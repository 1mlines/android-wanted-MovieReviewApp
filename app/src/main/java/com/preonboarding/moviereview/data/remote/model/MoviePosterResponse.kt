package com.preonboarding.moviereview.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviePosterResponse(
    @SerialName("Title") val title: String,
    @SerialName("Poster")val poster: String
)