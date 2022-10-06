package com.preonboarding.moviereview.data.api

import com.preonboarding.moviereview.data.remote.model.DailyBoxOfficeResponse
import com.preonboarding.moviereview.data.remote.model.MovieInfoResponse
import com.preonboarding.moviereview.data.remote.model.MovieListResponse
import com.preonboarding.moviereview.presentation.common.const.KOBIS_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisMovieApi {

    // 일별 박스오피스
    @GET("boxoffice/searchDailyBoxOfficeList.json")
    suspend fun searchDailyBoxOfficeList(
        @Query("key") key: String,
        @Query("targetDt") targetDt: String
    ): DailyBoxOfficeResponse

    // 상세정보
    @GET("movie/searchMovieInfo.json")
    suspend fun searchMovieInfo(
        @Query("key") key: String,
        @Query("targetDt") movieCd: String?
    ): MovieInfoResponse

    // 영화목록
    @GET("movie/searchMovieList.json")
    suspend fun getMovieList(
        @Query("key") key: String = KOBIS_API_KEY,
        @Query("movieNm") movieKrName: String = "",
        @Query("curPage") page: String
    ): MovieListResponse
}