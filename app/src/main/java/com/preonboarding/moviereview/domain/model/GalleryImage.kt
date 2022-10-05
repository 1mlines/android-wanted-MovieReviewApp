package com.preonboarding.moviereview.domain.model

import android.net.Uri

data class GalleryImage(
    val id: Long,
    val name: String,
    val filePath: String,
    val date: String,
    val imgUri: Uri,
    val type: ItemType
) {
    companion object {
        fun cameraItem() = GalleryImage(0, "", "", "", Uri.EMPTY, ItemType.CAMERA)
    }
}

enum class ItemType {
    CAMERA, IMAGE
}
