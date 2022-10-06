package com.preonboarding.moviereview.data.local.repository.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.preonboarding.moviereview.data.local.source.gallery.GalleryDataSource
import com.preonboarding.moviereview.data.paging.GalleryPagingSource
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.repository.local.gallery.GalleryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val galleryDataSource: GalleryDataSource
) : GalleryRepository {

    override fun getAllImages(): Flow<PagingData<GalleryImage>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            GalleryPagingSource(
                galleryDataSource = galleryDataSource
            )
        }.flow
    }

    companion object {
        private const val TAG = "GalleryRepositoryImpl"
        private const val PAGE_SIZE = 30
    }
}