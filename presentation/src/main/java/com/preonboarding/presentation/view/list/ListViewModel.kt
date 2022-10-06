package com.preonboarding.presentation.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.domain.model.Movie
import com.preonboarding.domain.model.Result
import com.preonboarding.domain.usecase.GetBoxOfficeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getBoxOfficeListUseCase: GetBoxOfficeListUseCase
) : ViewModel() {

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>> get() = _movies

    fun fetchMovieList() {
        viewModelScope.launch {
            when (val result = getBoxOfficeListUseCase.invoke()) {
                is Result.Success -> {
                    val list = result.data

                    Timber.w("ViewModel Success!")
                    Timber.w("$list")

                    _movies.postValue(list)
                }
                is Result.Error -> {
                    Timber.w("Error")
                }
            }
        }
    }
}