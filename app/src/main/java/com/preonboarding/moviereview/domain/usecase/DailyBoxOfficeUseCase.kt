package com.preonboarding.moviereview.domain.usecase

import com.preonboarding.moviereview.data.network.model.kobis.DailyBoxOffices

interface DailyBoxOfficeUseCase {
    suspend fun getDailyBoxOfficeList(key: String, targetDt: String, wideAreaCd: String): DailyBoxOffices?
}