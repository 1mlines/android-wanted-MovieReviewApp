package com.preonboarding.data.remote.response.movieinfo

import kotlinx.serialization.Serializable

@Serializable
data class MovieInfoResponse(
    val movieInfoResult: MovieInfoResult
)
