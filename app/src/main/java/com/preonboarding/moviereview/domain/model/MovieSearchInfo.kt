package com.preonboarding.moviereview.domain.model

data class MovieSearchInfo(
    val movieCode: String,
    val movieKrName: String,
    val movieEnName: String,
    val productionYear: String,
    val openDateTime: String,
    val genre: String
)