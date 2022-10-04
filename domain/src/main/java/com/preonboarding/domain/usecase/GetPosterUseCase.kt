package com.preonboarding.domain.usecase

import com.preonboarding.domain.repository.MovieRepository
import javax.inject.Inject

class GetPosterUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    //todo
}