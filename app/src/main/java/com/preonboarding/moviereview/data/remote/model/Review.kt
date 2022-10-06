package com.preonboarding.moviereview.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val content: String,
    val imageUrl: String,
    val nickName: String,
    val password: Int,
    val star: Float,
)
