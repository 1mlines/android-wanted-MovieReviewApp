package com.preonboarding.moviereview.presentation.ui.boxofficedetail

sealed class MoviePosterStatus {
    object Loading : MoviePosterStatus()
    class Failure(val msg: Throwable) : MoviePosterStatus()
    class Success(val data: String) : MoviePosterStatus()
    object Initial : MoviePosterStatus()
}
