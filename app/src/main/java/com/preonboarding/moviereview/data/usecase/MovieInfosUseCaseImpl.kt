package com.preonboarding.moviereview.data.usecase

import com.preonboarding.moviereview.data.network.model.kobis.MovieInfos
import com.preonboarding.moviereview.domain.repository.RemoteRepository
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import javax.inject.Inject

class MovieInfosUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
): MovieInfosUseCase {
    override suspend fun getMovieInfo(key: String, movieCd: String): MovieInfos? {
        return remoteRepository.getMoviesInfo(key, movieCd)
    }
}