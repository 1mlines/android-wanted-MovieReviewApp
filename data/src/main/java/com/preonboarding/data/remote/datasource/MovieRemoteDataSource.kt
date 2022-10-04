package com.preonboarding.data.remote.datasource

import com.preonboarding.domain.model.ExampleModel
import com.preonboarding.domain.model.Result

interface MovieRemoteDataSource {

    suspend fun getBoxOfficeList(date: String): Result<ExampleModel>

    suspend fun getMovieInfo(movieCd: String): Result<ExampleModel>

    suspend fun getPoster(movieNmEn: String): Result<ExampleModel>
}