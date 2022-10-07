package com.preonboarding.moviereview.presentation.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.data.remote.model.NetworkState
import com.preonboarding.moviereview.domain.usecase.GetMovieInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieInfoUseCase: GetMovieInfoUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<MovieDetailUiState> = MutableStateFlow(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    fun getMovieInfoByMovieCode(movieCode: String) {
        viewModelScope.launch {
            getMovieInfoUseCase.invoke(movieCode).collect { networkResult ->
                when (networkResult) {
                    is NetworkState.Success -> {
                        _uiState.value = MovieDetailUiState.Success(networkResult.data)
                    }
                }
            }
        }
    }
}