package com.preonboarding.moviereview.presentation.main

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.moviereview.domain.usecase.DailyBoxOfficeUseCase
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieInfosUseCase: MovieInfosUseCase,
    private val dailyBoxOfficeUseCase: DailyBoxOfficeUseCase
) : ViewModel() {
    fun getDailyBox(id: String, targetDt: String){
        viewModelScope.launch {
            val result = dailyBoxOfficeUseCase.getDailyBoxOfficeList(id, targetDt)
            Log.d("dailyBoxOffice", result.toString())
        }
    }
}