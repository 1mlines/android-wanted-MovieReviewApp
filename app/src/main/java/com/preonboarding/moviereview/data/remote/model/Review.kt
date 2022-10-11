package com.preonboarding.moviereview.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import javax.annotation.Nullable

@Serializable
data class Review(
    val content: String,
    var imageUrl: String,
    val nickName: String,
    val password: Int,
    val star: Float,
)

@Parcelize
data class ReviewInfo(
    val movieCd: String,
    val movieNm: String,
    val rank: String,
    val rankOldAndNew: String,
    @Nullable val postUrl: String?,
    val rankInten: String,
) : Parcelable