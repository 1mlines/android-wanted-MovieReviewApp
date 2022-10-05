package com.preonboarding.domain.repository

import com.preonboarding.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun uploadReview(title: String, review: Review)

    fun getReviewList(title: String): Flow<List<Review>>

    suspend fun deleteReview(title: String, review: Review)
}