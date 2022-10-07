package com.preonboarding.moviereview.data.local.source.gallery

import com.preonboarding.moviereview.domain.model.GalleryImage

interface GalleryDataSource {

    suspend fun getAllImages(pageNumber: Int): ArrayList<GalleryImage>
}