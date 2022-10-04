package com.preonboarding.domain.repository

import com.preonboarding.domain.model.ExampleModel
import com.preonboarding.domain.model.Result

interface MovieRepository {
    //todo
    suspend fun getBoxOfficeList(date: String): Result<ExampleModel>

    suspend fun getMovieInfo(movieCd: String): Result<ExampleModel>

    suspend fun getPoster(movieNmEn: String): Result<ExampleModel>
}