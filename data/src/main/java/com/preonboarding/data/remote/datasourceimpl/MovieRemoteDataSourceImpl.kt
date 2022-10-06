package com.preonboarding.data.remote.datasourceimpl

import com.preonboarding.data.remote.api.BoxOfficeService
import com.preonboarding.data.remote.api.MovieInfoService
import com.preonboarding.data.remote.api.PosterService
import com.preonboarding.data.remote.datasource.MovieRemoteDataSource
import com.preonboarding.data.remote.response.boxoffice.BoxOfficeListResponse
import com.preonboarding.data.remote.response.movieinfo.MovieInfoResponse
import com.preonboarding.data.remote.response.poster.PosterResponse
import com.preonboarding.domain.model.Result
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val boxOfficeService: BoxOfficeService,
    private val movieInfoService: MovieInfoService,
    private val posterService: PosterService,
) : MovieRemoteDataSource {

    override suspend fun getBoxOfficeList(): Result<BoxOfficeListResponse> {
        val response = boxOfficeService.getBoxOfficeList()
        return try {
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(IllegalArgumentException("example message"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMovieInfo(movieCd: String): Result<MovieInfoResponse> {
        val response = movieInfoService.getMovieInfo(movieCd)
        return try {
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(IllegalArgumentException("example message"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPoster(movieNmEn: String): Result<PosterResponse> {
        val response = posterService.getPoster(movieNmEn)
        return try {
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(IllegalArgumentException("example message"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}