package com.preonboarding.moviereview.data.remote.repository

import com.preonboarding.moviereview.data.api.KobisMovieApi
import com.preonboarding.moviereview.data.api.OmdbMovieApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepositoryImpl @Inject constructor(
    private val kobisMovieApi: KobisMovieApi,
    private val omdbMovieApi: OmdbMovieApi
) {
    // API 호출
}