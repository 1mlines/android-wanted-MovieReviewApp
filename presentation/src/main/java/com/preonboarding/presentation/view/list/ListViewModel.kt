package com.preonboarding.presentation.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preonboarding.domain.model.Movie
import com.preonboarding.domain.usecase.GetBoxOfficeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getBoxOfficeListUseCase: GetBoxOfficeListUseCase
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>> get() = _movies

    fun fetchMovieList() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            _movies.postValue(getBoxOfficeListUseCase.invoke())
            _isLoading.postValue(false)
        }
    }
}