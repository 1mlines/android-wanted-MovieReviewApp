package com.preonboarding.data.remote.response.movieinfo

import kotlinx.serialization.Serializable

@Serializable
data class MovieInfoResult(
    val movieInfo: MovieInfo,
    val source: String
)