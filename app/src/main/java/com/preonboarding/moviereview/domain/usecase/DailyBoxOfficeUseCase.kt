package com.preonboarding.moviereview.domain.usecase

import com.preonboarding.moviereview.data.network.model.DailyBoxOffices


interface DailyBoxOfficeUseCase {
    suspend fun getDailyBoxOfficeList(key: String, targetDt: String): DailyBoxOffices?
}