package com.preonboarding.moviereview.domain.model

import com.google.firebase.Timestamp

data class ReviewVo (
    val contents:String = "",
    val imageUrl:String = "",
    val movieCd:String = "",
    val name:String = "",
    val password:String = "",
    val time: Timestamp? = null,
    val star: Int = 404
)