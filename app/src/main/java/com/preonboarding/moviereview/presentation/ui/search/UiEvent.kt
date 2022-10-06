package com.preonboarding.moviereview.presentation.ui.search

sealed class UiEvent {
    data class ShowError(val error: String) : UiEvent()
}
