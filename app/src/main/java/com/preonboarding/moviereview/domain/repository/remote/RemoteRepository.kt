package com.preonboarding.moviereview.domain.repository.remote

import com.preonboarding.moviereview.data.remote.model.DailyBoxOfficeResponse
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun searchDailyBoxOfficeList(
        key: String,
        targetDt: String
    ): Flow<DailyBoxOfficeResponse>
}