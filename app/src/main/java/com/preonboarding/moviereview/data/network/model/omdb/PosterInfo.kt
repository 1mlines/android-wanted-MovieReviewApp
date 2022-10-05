package com.preonboarding.moviereview.data.network.model.omdb

import kotlinx.serialization.Serializable

@Serializable
data class PosterInfo (
    val Title: String,
    val Poster: String
)