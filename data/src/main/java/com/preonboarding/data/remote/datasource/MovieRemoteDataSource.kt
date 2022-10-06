package com.preonboarding.data.remote.datasource

import com.preonboarding.data.remote.response.boxoffice.BoxOfficeListResponse
import com.preonboarding.data.remote.response.boxoffice.BoxOfficeResult
import com.preonboarding.data.remote.response.movieinfo.MovieInfo
import com.preonboarding.data.remote.response.movieinfo.MovieInfoResponse
import com.preonboarding.data.remote.response.poster.PosterResponse
import com.preonboarding.domain.model.ExampleModel
import com.preonboarding.domain.model.Result

interface MovieRemoteDataSource {

    suspend fun getBoxOfficeList(): Result<BoxOfficeListResponse>

    suspend fun getMovieInfo(movieCd: String): Result<MovieInfoResponse>

    suspend fun getPoster(movieNmEn: String): Result<PosterResponse>
}