package com.preonboarding.data.remote.api

import com.preonboarding.data.common.constant.SEARCH_BOX_OFFICE
import com.preonboarding.data.common.constant.SEARCH_MOVIE_INFO
import com.preonboarding.data.remote.response.boxoffice.BoxOfficeListResponse
import com.preonboarding.data.remote.response.movieinfo.MovieInfoResponse
import com.preonboarding.data.remote.response.poster.PosterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BoxOfficeService {

    //todo key, page
    @GET(SEARCH_BOX_OFFICE)
    suspend fun getBoxOfficeList(
        @Query("targetDt") date: String,
    ): Response<BoxOfficeListResponse>

}