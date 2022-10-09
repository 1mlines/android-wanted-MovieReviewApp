package com.preonboarding.domain.usecase

import com.preonboarding.domain.repository.MovieRepository
import javax.inject.Inject

class GetBoxOfficeListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() =
        movieRepository.getBoxOfficeList()
}