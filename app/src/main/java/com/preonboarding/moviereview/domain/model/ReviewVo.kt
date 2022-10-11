package com.preonboarding.moviereview.domain.model

import com.google.firebase.Timestamp

data class Reviews(
    val reviews: List<ReviewVo>
)
data class ReviewVo(
    val contents: String = "",
    val imageUrl: String = "",
    val movieCd: String = "",
    val name: String = "",
    val password: String = "",
    val time: Timestamp = Timestamp.now(),
    val star: Float = 0f
)