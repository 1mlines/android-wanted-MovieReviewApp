package com.preonboarding.moviereview.data.usecase

import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo
import com.preonboarding.moviereview.domain.repository.RemoteRepository
import com.preonboarding.moviereview.domain.usecase.PosterInfoUseCase
import javax.inject.Inject

class PosterInfoUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
): PosterInfoUseCase{
    override suspend fun getPosterInfo(title: String, key: String): PosterInfo? {
        return remoteRepository.getPosterInfo(title, key)
    }
}