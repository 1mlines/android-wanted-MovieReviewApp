package com.preonboarding.moviereview.presentation.ui.detail

import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie
import com.preonboarding.moviereview.data.remote.model.MovieInfoResponse
import com.preonboarding.moviereview.data.remote.model.Review

sealed class ReviewStatus {
    object Initial: ReviewStatus()
    object Loading: ReviewStatus()
    class Failure(val msg: Throwable) : ReviewStatus()
    class Success(val data: List<Review>) : ReviewStatus()
}