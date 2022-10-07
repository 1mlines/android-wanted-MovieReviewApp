package com.preonboarding.moviereview.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieSearchInfo(
    val movieCode: String,
    val movieKrName: String,
    val movieEnName: String,
    val productionYear: String,
    val openDateTime: String,
    val genre: String
) : Parcelable