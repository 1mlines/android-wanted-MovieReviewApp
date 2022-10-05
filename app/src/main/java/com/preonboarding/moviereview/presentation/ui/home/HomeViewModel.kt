package com.preonboarding.moviereview.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import com.preonboarding.moviereview.presentation.common.base.BaseViewModel
import com.preonboarding.moviereview.presentation.common.const.KOBIS_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
): BaseViewModel() {

    fun searchDailyBoxOfficeList() {
        viewModelScope.launch {
            remoteRepository.searchDailyBoxOfficeList(
                key = KOBIS_API_KEY,
                targetDt = "20120101"
            )
                .collect { dailyBoxofficeRes ->
                    Timber.tag(TAG).e(dailyBoxofficeRes.toString())

                    dailyBoxofficeRes.boxOfficeResult.dailyBoxOfficeList.map { movie ->
                        remoteRepository.searchMovieInfo(
                            key = KOBIS_API_KEY,
                            movieCd = movie.movieCd
                        )
                            .collect { movieInfoRes ->
                                Timber.tag(TAG).e(movieInfoRes.toString())
                            }
                    }

                }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}