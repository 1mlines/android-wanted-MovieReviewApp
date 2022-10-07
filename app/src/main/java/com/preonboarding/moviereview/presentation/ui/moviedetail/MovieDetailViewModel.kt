package com.preonboarding.moviereview.presentation.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.data.remote.model.NetworkState
import com.preonboarding.moviereview.domain.model.MovieInfo
import com.preonboarding.moviereview.domain.model.MoviePoster
import com.preonboarding.moviereview.domain.usecase.GetMovieInfoUseCase
import com.preonboarding.moviereview.domain.usecase.GetMoviePosterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieInfoUseCase: GetMovieInfoUseCase,
    private val getMoviePosterUseCase: GetMoviePosterUseCase
) : ViewModel() {

    private var _movieInfoUiState: MutableStateFlow<MovieDetailUiState<MovieInfo>> = MutableStateFlow(MovieDetailUiState.Loading)
    val movieInfoUiState: StateFlow<MovieDetailUiState<MovieInfo>> = _movieInfoUiState.asStateFlow()

    private var _moviePosterUiState: MutableStateFlow<MovieDetailUiState<MoviePoster>> = MutableStateFlow(MovieDetailUiState.Loading)
    val moviePosterUiState: StateFlow<MovieDetailUiState<MoviePoster>> = _moviePosterUiState.asStateFlow()

    fun getMovieInfoByMovieCode(movieCode: String) {
        viewModelScope.launch {
            getMovieInfoUseCase.invoke(movieCode).collect { networkState ->
                when (networkState) {
                    is NetworkState.Success -> {
                        _movieInfoUiState.value = MovieDetailUiState.Success(networkState.data)
                    }
                }
            }
        }
    }

    fun getMoviePosterByMovieName(movieName: String) {
        viewModelScope.launch {
            getMoviePosterUseCase.invoke(movieName).collect { networkState ->
                when (networkState) {
                    is NetworkState.Success -> {
                        _moviePosterUiState.value = MovieDetailUiState.Success(networkState.data)
                    }
                }
            }
        }
    }
}