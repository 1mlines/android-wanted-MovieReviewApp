package com.preonboarding.domain.usecase

import com.preonboarding.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetReviewListUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(title: String) = firebaseRepository.getReviewList(title)
}