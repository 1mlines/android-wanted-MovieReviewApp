package com.preonboarding.moviereview.data.remote.repository

import com.preonboarding.moviereview.data.api.KobisMovieApi
import com.preonboarding.moviereview.data.api.OmdbMovieApi
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepositoryImpl @Inject constructor(
    private val kobisMovieApi: KobisMovieApi,
    private val omdbMovieApi: OmdbMovieApi
): RemoteRepository {

    // add override suspend fun

}