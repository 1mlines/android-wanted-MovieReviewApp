package com.preonboarding.data.remote.response.boxoffice

import kotlinx.serialization.Serializable

@Serializable
data class DailyBoxOffice(
    val audiAcc: String, //누적관객수
    val audiChange: String, //전일대비 관객수 증감비율
    val audiCnt: String, //당일관객수
    val audiInten: String, //전일대비 곽객수 증감수
    val movieCd: String, //영화코드
    val movieNm: String, //영화명,국문
    val openDt: String, //개봉일
    val rank: String, //랭크
    val rankInten: String,//랭크 증감
    val rankOldAndNew: String, //신규여부
    val rnum: String, //순번
    val salesAcc: String,
    val salesAmt: String,
    val salesChange: String,
    val salesInten: String,
    val salesShare: String,
    val scrnCnt: String,
    val showCnt: String,
    val posetURL : String
)