package com.preonboarding.moviereview.domain.usecase

import com.preonboarding.moviereview.data.remote.model.NetworkState
import com.preonboarding.moviereview.domain.model.MoviePoster
import com.preonboarding.moviereview.domain.repository.remote.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviePosterUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieName: String): Flow<NetworkState<MoviePoster>> {
        return movieRepository.getMoviePosterByMovieName(movieName)
    }
}