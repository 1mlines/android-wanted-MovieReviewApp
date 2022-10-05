package com.preonboarding.moviereview.domain.model

import android.net.Uri

data class GalleryImage(
    val id: Long,
    val name: String,
    val filePath: String,
    val date: String,
    val imgUri: Uri,
)
