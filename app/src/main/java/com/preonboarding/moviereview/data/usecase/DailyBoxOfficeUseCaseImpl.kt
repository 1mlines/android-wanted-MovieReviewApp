package com.preonboarding.moviereview.data.usecase

import com.preonboarding.moviereview.data.network.model.DailyBoxOffices
import com.preonboarding.moviereview.domain.repository.RemoteRepository
import com.preonboarding.moviereview.domain.usecase.DailyBoxOfficeUseCase
import javax.inject.Inject

class DailyBoxOfficeUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
): DailyBoxOfficeUseCase {

    override suspend fun getDailyBoxOfficeList(key: String, targetDt: String): DailyBoxOffices? {
        return remoteRepository.getDailyBoxOfficeList(key, targetDt)
    }
}