package com.preonboarding.moviereview.presentation.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import com.preonboarding.moviereview.domain.usecase.GetDailyMovieUseCase
import com.preonboarding.moviereview.presentation.common.base.BaseViewModel
import com.preonboarding.moviereview.presentation.common.const.KOBIS_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyMovieUseCase: GetDailyMovieUseCase
) : BaseViewModel() {

    private var _checkHomeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Empty)
    val checkHomeState: StateFlow<HomeState> = _checkHomeState

    suspend fun getData(key: String, targetDt: String) =
        getDailyMovieUseCase.invoke(key, targetDt)


    fun getDailyMovie(key: String, targetDt: String) =
        viewModelScope.launch {
            _checkHomeState.value = HomeState.Loading
            getDailyMovieUseCase.invoke(key, targetDt)
                .catch { e ->
                    _checkHomeState.value = HomeState.Failure(e)
                }.collectLatest { movieData ->
                    _checkHomeState.value = HomeState.Success(movieData)
                }
        }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}