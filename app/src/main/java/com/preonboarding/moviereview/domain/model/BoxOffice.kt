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
    val movieOpen: String,
    val isReady: Boolean
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
            movieOpen = "2022/02/02",
            false
        )

        val DUMMY_LIST = listOf(
            BoxOffice(
                movieCd = "1",
                ranking = "",
                rankInten = "",
                rankType = "",
                movieName = "",
                audiAcc = "",
                moviePoster = "",
                movieOpen = "",
                false
            ),
            BoxOffice(
                movieCd = "2",
                ranking = "",
                rankInten = "",
                rankType = "",
                movieName = "",
                audiAcc = "",
                moviePoster = "",
                movieOpen = "",
                false
            ),
            BoxOffice(
                movieCd = "3",
                ranking = "",
                rankInten = "",
                rankType = "",
                movieName = "",
                audiAcc = "",
                moviePoster = "",
                movieOpen = "",
                false
            ),
            BoxOffice(
                movieCd = "4",
                ranking = "",
                rankInten = "",
                rankType = "",
                movieName = "",
                audiAcc = "",
                moviePoster = "",
                movieOpen = "",
                false
            ),
            BoxOffice(
                movieCd = "5",
                ranking = "",
                rankInten = "",
                rankType = "",
                movieName = "",
                audiAcc = "",
                moviePoster = "",
                movieOpen = "",
                false
            ),
            BoxOffice(
                movieCd = "6",
                ranking = "",
                rankInten = "",
                rankType = "",
                movieName = "",
                audiAcc = "",
                moviePoster = "",
                movieOpen = "",
                false
            ),
            BoxOffice(
                movieCd = "7",
                ranking = "",
                rankInten = "",
                rankType = "",
                movieName = "",
                audiAcc = "",
                moviePoster = "",
                movieOpen = "",
                false
            )
        )
    }
}
