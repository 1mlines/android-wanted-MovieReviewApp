package com.preonboarding.moviereview.presentation.ui.moviedetail

import com.preonboarding.moviereview.domain.model.MovieInfo

sealed class MovieDetailUiState<out T: Any> {
    object Loading: MovieDetailUiState<Nothing>()

    data class Success<out T: Any>(val data: T): MovieDetailUiState<T>()
}
