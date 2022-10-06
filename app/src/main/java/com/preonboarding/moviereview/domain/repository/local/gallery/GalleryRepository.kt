package com.preonboarding.moviereview.domain.repository.local.gallery

import androidx.paging.PagingData
import com.preonboarding.moviereview.domain.model.GalleryImage
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {

    fun getAllImages(): Flow<PagingData<GalleryImage>>
}