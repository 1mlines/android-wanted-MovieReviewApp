package com.preonboarding.moviereview.domain.usecase

import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import javax.inject.Inject

class GetDailyMovieUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun invoke(key: String, targetDt: String) =
        remoteRepository.getDailyMovie(key, targetDt)

}