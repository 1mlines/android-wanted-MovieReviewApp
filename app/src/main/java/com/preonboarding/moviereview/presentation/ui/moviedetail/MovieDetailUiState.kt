package com.preonboarding.moviereview.presentation.ui.moviedetail

import com.preonboarding.moviereview.domain.model.MovieInfo

sealed class MovieDetailUiState {
    object Loading: MovieDetailUiState()

    data class Success(val data: MovieInfo): MovieDetailUiState()
}
