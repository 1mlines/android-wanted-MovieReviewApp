package com.preonboarding.data.remote.response.movieinfo

import com.preonboarding.domain.model.ExampleModel
import kotlinx.serialization.Serializable

@Serializable
data class MovieInfoResponse(
    val movieInfoResult: MovieInfoResult
){
    fun toDomainModel() = ExampleModel(null)
}