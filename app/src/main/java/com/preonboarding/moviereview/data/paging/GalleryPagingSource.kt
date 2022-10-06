package com.preonboarding.moviereview.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.preonboarding.moviereview.data.local.source.gallery.GalleryDataSource
import com.preonboarding.moviereview.domain.model.GalleryImage
import kotlinx.coroutines.delay
import javax.inject.Inject

class GalleryPagingSource @Inject constructor(
    private val galleryDataSource: GalleryDataSource
): PagingSource<Int, GalleryImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryImage> {
        return try {
            val pageNumber = params.key ?: START_PAGE // curKey

            if(pageNumber != START_PAGE) delay(DELAY_MILLIS)

            val imageList = galleryDataSource.getAllImages(pageNumber = pageNumber)

            LoadResult.Page(
                data = imageList,
                prevKey = if (pageNumber == START_PAGE) null else pageNumber - 1,
                nextKey = if (imageList.isEmpty()) null else pageNumber + (params.loadSize / PAGE_SIZE),
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val START_PAGE = 1
        private const val PAGE_SIZE = 30
        private const val DELAY_MILLIS = 1_000L
        private const val TAG = "GalleryPagingSource"
    }
}