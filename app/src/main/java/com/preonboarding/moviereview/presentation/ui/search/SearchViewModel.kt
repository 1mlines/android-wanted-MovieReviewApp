package com.preonboarding.moviereview.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.preonboarding.moviereview.domain.model.MovieSearchInfo
import com.preonboarding.moviereview.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<PagingData<MovieSearchInfo>> =
        MutableStateFlow(PagingData.empty())
    val uiState: StateFlow<PagingData<MovieSearchInfo>> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun searchMovie(movieNameQuery: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                searchMovieUseCase.invoke(movieNameQuery)
                    .cachedIn(viewModelScope)
                    .collectLatest { pagedSearchResult ->
                        _uiState.emit(pagedSearchResult)
                    }
            }.onFailure {
                showErrorMessage(it.message)
            }
        }
    }

    private fun emitError(event: UiEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun showErrorMessage(message: String?) {
        message?.let {
            emitError(UiEvent.ShowError(it))
        }
    }
}