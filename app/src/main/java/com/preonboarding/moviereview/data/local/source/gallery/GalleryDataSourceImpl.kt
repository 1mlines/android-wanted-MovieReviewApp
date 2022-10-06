package com.preonboarding.moviereview.data.local.source.gallery

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.model.ItemType
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class GalleryDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): GalleryDataSource {

    override suspend fun getAllImages(pageNumber: Int): ArrayList<GalleryImage> {
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
            while(cursor.moveToNext() && cursor.position < PAGE_SIZE * pageNumber){

                val id = cursor.getLong(cursor.getColumnIndexOrThrow(IMAGE_ID))
                val filepath = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_DATA))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_NAME))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_DATE_ADDED))

                // 사진 경로 Uri 가져오기
                val uri = ContentUris.withAppendedId(IMAGE_URI, id)

                if(cursor.position >= (pageNumber - 1) * PAGE_SIZE)
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
        if(pageNumber == 1) imageList.add(0, GalleryImage.cameraItem())
        return imageList
    }

    companion object {
        private val IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        private const val IMAGE_ID = MediaStore.Images.ImageColumns._ID
        private const val IMAGE_DATA = MediaStore.Images.ImageColumns.DATA
        private const val IMAGE_NAME = MediaStore.Images.ImageColumns.DISPLAY_NAME
        private const val IMAGE_DATE_ADDED = MediaStore.Images.ImageColumns.DATE_ADDED
        private const val IMAGE_DATE_TAKEN = MediaStore.Images.ImageColumns.DATE_TAKEN

        private const val PAGE_SIZE = 30
        private const val TAG = "GalleryImageDataSourceImpl"
    }
}