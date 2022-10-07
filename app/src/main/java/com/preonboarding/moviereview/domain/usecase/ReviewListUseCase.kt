package com.preonboarding.moviereview.domain.usecase

import com.google.firebase.firestore.CollectionReference
import com.preonboarding.moviereview.domain.model.ReviewVo

interface ReviewListUseCase {
    suspend fun getReviews( movieCd : String) : CollectionReference
}