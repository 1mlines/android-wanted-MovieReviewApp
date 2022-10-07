package com.preonboarding.moviereview.presentation.common.util.image

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.FileOutputStream

@SuppressLint("Recycle")
fun saveFile(context: Context, bitmap: Bitmap): Uri {
    val cv = ContentValues()
    cv.put(MediaStore.Images.Media.DISPLAY_NAME, "Movie-${System.currentTimeMillis()}")
    cv.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        cv.put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)

    if (uri != null) {
        val scriptor = context.contentResolver.openFileDescriptor(uri, "w")

        if (scriptor != null) {
            val fos = FileOutputStream(scriptor.fileDescriptor)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                cv.clear()
                cv.put(MediaStore.Images.Media.IS_PENDING, 0)
                context.contentResolver.update(uri, cv, null, null)
            }
        }
    }

    return uri ?: Uri.EMPTY
}