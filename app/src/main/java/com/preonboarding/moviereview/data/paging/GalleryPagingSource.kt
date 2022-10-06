package com.preonboarding.moviereview.data.paging

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.preonboarding.moviereview.data.local.source.gallery.GalleryDataSource
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.model.ItemType
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

class GalleryPagingSource @Inject constructor(
    private val galleryDataSource: GalleryDataSource
): PagingSource<Int, GalleryImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryImage> {
        return try {
            val curKey = params.key ?: START_KEY

            if(curKey != START_KEY) delay(DELAY_MILLIS)

            val imageList = galleryDataSource.getAllImages()
            Timber.tag(TAG).e(imageList.toString())

            LoadResult.Page(
                data = imageList,
                prevKey = if (curKey == START_KEY) null else curKey - 1,
                nextKey = if (imageList.isEmpty()) null else curKey + 1,
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

    private fun getAllImages(context: Context): ArrayList<GalleryImage> {
        val imageList: ArrayList<GalleryImage> = arrayListOf()

        val projection = arrayOf(
            IMAGE_ID,
            IMAGE_DATA,
            IMAGE_NAME,
            IMAGE_DATE_ADDED
        )

        // 가져올 위치를 지정한다. SQL 쿼리 식과 비슷하게 생성
        val selection: String? = null
        val selectionArgs: Array<String>? = null

        val cursor = context.contentResolver.query(
            IMAGE_URI,
            projection,
            selection,
            selectionArgs,
            "$IMAGE_DATE_TAKEN DESC"
        )

        if(cursor != null){
            while(cursor.moveToNext()){

                val id = cursor.getLong(cursor.getColumnIndexOrThrow(IMAGE_ID))
                val filepath = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_DATA))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_NAME))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_DATE_ADDED))

                // 사진 경로 Uri 가져오기
                val uri = ContentUris.withAppendedId(IMAGE_URI, id)

                imageList.add(GalleryImage(
                    id = id,
                    name = name,
                    filePath = filepath,
                    date = date,
                    imgUri = uri,
                    type = ItemType.IMAGE
                ))
            }
            cursor.close()
        }
        Timber.tag(TAG).e(imageList.toString())
        return imageList
    }

    companion object {
        private val IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        private const val IMAGE_ID = MediaStore.Images.ImageColumns._ID
        private const val IMAGE_DATA = MediaStore.Images.ImageColumns.DATA
        private const val IMAGE_NAME = MediaStore.Images.ImageColumns.DISPLAY_NAME
        private const val IMAGE_DATE_ADDED = MediaStore.Images.ImageColumns.DATE_ADDED
        private const val IMAGE_DATE_TAKEN = MediaStore.Images.ImageColumns.DATE_TAKEN

        private const val START_KEY = 1
        private const val DELAY_MILLIS = 1_000L
        private const val TAG = "GalleryPagingSource"
    }
}