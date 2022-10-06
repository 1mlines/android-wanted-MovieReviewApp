package com.preonboarding.moviereview.presentation.ui.detail

import com.preonboarding.moviereview.data.remote.model.MoviePosterResponse

sealed class MoviePosterStatus {
    object Loading : MoviePosterStatus()
    class Failure(val msg: Throwable) : MoviePosterStatus()
    class Success(val data: MoviePosterResponse) : MoviePosterStatus()
    object Empty : MoviePosterStatus()
}
