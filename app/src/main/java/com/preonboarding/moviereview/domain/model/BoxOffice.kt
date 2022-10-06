package com.preonboarding.moviereview.domain.model

import kotlinx.serialization.Serializable
/**
 * @Created by 김현국 2022/10/05
 */
@Serializable
data class BoxOffice(
    val movieCd: String,
    val ranking: String,
    val rankInten: String,
    val rankType: String,
    val movieName: String,
    val audiAcc: String,
    val moviePoster: String,
    val movieOpen: String
) {
    companion object {
        val DUMMY = BoxOffice(
            movieCd = "",
            ranking = "",
            rankInten = "",
            rankType = "OLD",
            movieName = "DUMMY",
            audiAcc = "",
            moviePoster = "",
            movieOpen = "2022/02/02"
        )
    }
}
