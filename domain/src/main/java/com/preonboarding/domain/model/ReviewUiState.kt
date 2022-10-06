package com.preonboarding.domain.model

sealed class ReviewUiState {

    data class Success(
        val mode: MODE
    ) : ReviewUiState()

    data class Failure(
        val mode: MODE
    ) : ReviewUiState()

    object Modify : ReviewUiState()

    object Reading : ReviewUiState()

    object Review : ReviewUiState()
}

enum class MODE {
    DELETE, REVIEW, MODIFY, VALIDATION
}