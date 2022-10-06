package com.preonboarding.moviereview.domain.usecase

import androidx.paging.PagingData
import com.preonboarding.moviereview.di.IoDispatcher
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.repository.local.gallery.GalleryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetGalleryImageUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<PagingData<GalleryImage>> =
        galleryRepository.getAllImages().flowOn(dispatcher)
}