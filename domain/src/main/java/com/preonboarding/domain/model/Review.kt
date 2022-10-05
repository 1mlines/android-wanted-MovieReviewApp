package com.preonboarding.domain.model

data class Review(
    val nickname: String,
    val password: String,
    val rating: Int,
    val content: String = "",
    val imageUrl: String = "",
    val date: String
)
