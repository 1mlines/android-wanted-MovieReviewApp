package com.preonboarding.moviereview.domain.model

import java.sql.Timestamp

data class ReviewVO(
    val contents: String = "",
    val imageUrl: String = "",
    val movie_id: String = "",
    val name: String = "",
    val password: String = "",
    val time: Timestamp = Timestamp(System.currentTimeMillis()),
    val star: Float
)

data class Reviews(
    val reviews: List<ReviewVO>
)