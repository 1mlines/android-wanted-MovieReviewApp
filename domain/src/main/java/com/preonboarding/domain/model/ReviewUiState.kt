package com.preonboarding.domain.model

sealed class ReviewUiState {

    object Success: ReviewUiState()

    object Failure: ReviewUiState()

    object Modify : ReviewUiState()

    object Empty : ReviewUiState()

    object Reading: ReviewUiState()
}
