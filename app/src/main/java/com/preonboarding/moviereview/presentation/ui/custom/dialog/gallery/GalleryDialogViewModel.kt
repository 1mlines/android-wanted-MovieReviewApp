package com.preonboarding.moviereview.presentation.ui.custom.dialog.gallery

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.preonboarding.moviereview.data.paging.GalleryPagingSource
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.model.ItemType
import com.preonboarding.moviereview.domain.usecase.GetGalleryImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GalleryDialogViewModel @Inject constructor(
    private val getGalleryImageUseCase: GetGalleryImageUseCase,
): ViewModel() {

    private val _imageList: MutableStateFlow<PagingData<GalleryImage>> =
        MutableStateFlow(PagingData.empty())
    val imageList: StateFlow<PagingData<GalleryImage>>
        get() = _imageList

    fun getAllImages(): Flow<PagingData<GalleryImage>> {
        return getGalleryImageUseCase.invoke().cachedIn(viewModelScope)
    }

    companion object {
        private const val TAG = "GalleryViewModel"
    }
}