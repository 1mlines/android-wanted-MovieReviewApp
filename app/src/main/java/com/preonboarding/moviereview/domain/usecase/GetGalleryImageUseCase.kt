package com.preonboarding.moviereview.domain.usecase

import androidx.paging.PagingData
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.repository.local.gallery.GalleryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGalleryImageUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository
) {
    operator fun invoke(): Flow<PagingData<GalleryImage>> =
        galleryRepository.getAllImages()
}