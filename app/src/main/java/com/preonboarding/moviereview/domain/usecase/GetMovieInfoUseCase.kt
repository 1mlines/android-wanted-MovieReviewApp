package com.preonboarding.moviereview.domain.usecase

import com.preonboarding.moviereview.data.remote.model.NetworkResult
import com.preonboarding.moviereview.domain.model.MovieInfo
import com.preonboarding.moviereview.domain.repository.remote.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieInfoUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieCode: String): Flow<NetworkResult<MovieInfo>> {
        return movieRepository.getMovieInfoByCode(movieCode)
    }
}