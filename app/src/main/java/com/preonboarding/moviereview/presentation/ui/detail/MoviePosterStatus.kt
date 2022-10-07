package com.preonboarding.moviereview.presentation.ui.detail

sealed class MoviePosterStatus {
    object Loading : MoviePosterStatus()
    class Failure(val msg: Throwable) : MoviePosterStatus()
    class Success(val data: String) : MoviePosterStatus()
    object Initial : MoviePosterStatus()
}
