package com.preonboarding.moviereview.data.mapper

import com.preonboarding.moviereview.data.network.model.kobis.DailyBoxOffice
import com.preonboarding.moviereview.domain.model.BoxOffice

/**
 * @Created by 김현국 2022/10/05
 */

fun DailyBoxOffice.asModel(): BoxOffice {
    return BoxOffice(
        movieCd = this.movieCd,
        ranking = this.rank,
        rankInten = this.rankInten,
        rankType = this.rankOldAndNew,
        movieName = this.movieNm,
        movieOpen = this.openDt,
        moviePoster = "",
        audiAcc = this.audiAcc
    )
}
