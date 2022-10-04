package com.preonboarding.data.remote.response.boxoffice

import com.preonboarding.domain.model.ExampleModel


data class BoxOfficeListResponse(
    val boxOfficeResult: BoxOfficeResult,
) {
    fun toDomainModel() = ExampleModel(null)
}