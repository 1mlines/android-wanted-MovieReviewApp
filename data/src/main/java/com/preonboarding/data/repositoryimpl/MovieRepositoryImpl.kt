package com.preonboarding.data.repositoryimpl

import com.preonboarding.data.remote.datasource.MovieRemoteDataSource
import com.preonboarding.domain.model.ExampleModel
import com.preonboarding.domain.model.Result
import com.preonboarding.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override suspend fun getBoxOfficeList(date: String): Result<ExampleModel> =
        movieRemoteDataSource.getBoxOfficeList(date)

    override suspend fun getMovieInfo(movieCd: String): Result<ExampleModel> =
        movieRemoteDataSource.getMovieInfo(movieCd)

    override suspend fun getPoster(movieNmEn: String): Result<ExampleModel> =
        movieRemoteDataSource.getPoster(movieNmEn)

}