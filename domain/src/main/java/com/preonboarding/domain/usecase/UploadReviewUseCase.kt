package com.preonboarding.domain.usecase

import com.preonboarding.domain.model.Review
import com.preonboarding.domain.repository.FirebaseRepository
import javax.inject.Inject

class UploadReviewUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(title: String, review: Review) =
        firebaseRepository.uploadReview(title, review)
}