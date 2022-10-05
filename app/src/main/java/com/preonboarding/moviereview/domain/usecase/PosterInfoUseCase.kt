package com.preonboarding.moviereview.domain.usecase

import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo

interface PosterInfoUseCase {
    suspend fun getPosterInfo(title: String, key: String): PosterInfo?
}