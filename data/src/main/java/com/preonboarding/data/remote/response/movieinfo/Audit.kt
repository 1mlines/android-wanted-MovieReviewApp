package com.preonboarding.data.remote.response.movieinfo

import kotlinx.serialization.Serializable

@Serializable
data class Audit(
    val auditNo: String,
    val watchGradeNm: String
)