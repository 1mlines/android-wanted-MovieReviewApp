package com.preonboarding.data.remote.response.boxoffice

import com.preonboarding.domain.model.ExampleModel
import kotlinx.serialization.Serializable

@Serializable
data class BoxOfficeListResponse(
    val boxOfficeResult: BoxOfficeResult,
) {
    fun toDomainModel() = ExampleModel(null)
}