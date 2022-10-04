package com.preonboarding.domain.usecase

import com.preonboarding.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieInfoUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    //todo
}