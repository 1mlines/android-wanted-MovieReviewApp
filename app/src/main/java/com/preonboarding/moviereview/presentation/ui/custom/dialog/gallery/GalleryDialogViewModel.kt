package com.preonboarding.moviereview.presentation.ui.custom.dialog.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.usecase.GetGalleryImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GalleryDialogViewModel @Inject constructor(
    private val getGalleryImageUseCase: GetGalleryImageUseCase,
): ViewModel() {

    private val _cameraImage = MutableStateFlow<GalleryImage>(GalleryImage.emptyItem())
    val cameraImage: StateFlow<GalleryImage>
        get() = _cameraImage

    fun setCameraImage(uri: Uri?) {

        _cameraImage.value = _cameraImage.value.copy(
            id = System.currentTimeMillis() / 7,
            name = "",
            filePath = "",
            date = "",
            imgUri = uri ?: Uri.EMPTY
        )
    }

    fun getAllImages(): Flow<PagingData<GalleryImage>> {
        return getGalleryImageUseCase.invoke().cachedIn(viewModelScope)
    }

    companion object {
        private const val TAG = "GalleryViewModel"
    }
}