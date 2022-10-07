package com.preonboarding.moviereview.domain.repository

import com.preonboarding.moviereview.domain.model.ReviewVo

interface FirebaseRepository {
    suspend fun getReviewList(movieCd: String) : List<ReviewVo>?
}




