package com.preonboarding.moviereview.domain.usecase

import com.preonboarding.moviereview.data.network.model.omdb.PosterInfo

interface PosterInfoUseCase {
    suspend fun getPosterInfo(title: String, key: String): PosterInfo?
}
//http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101
//https://www.omdbapi.com/?t=frozen&apikey=fa8d1585