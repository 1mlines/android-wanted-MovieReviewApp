package com.preonboarding.moviereview.presentation.ui.detail

import com.preonboarding.moviereview.data.remote.model.MovieInfoResponse

sealed class MovieStatus {
    object Loading : MovieStatus()
    class Failure(val msg: Throwable) : MovieStatus()
    class Success(val data: MovieInfoResponse) : MovieStatus()
    object Empty : MovieStatus()
}
