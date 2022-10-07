package com.preonboarding.domain.model

import java.io.Serializable

/**
 * 일별 박스오피스
 * @property id : 영화 ID
 * @property rank : 영화 랭킹
 * @property rankInten : 전일 대비 순위의 증감분
 * @property rankOldAndNew : 랭킹 신규 진입 여부
 * @property name : 영화 이름
 * @property enName : 영화 영문 이름
 * @property openDt : 개봉일
 * @property audiAcc : 관객수
 * @property prdtYear : 제작연도
 * @property showTm : 상영시간
 * @property genreNm : 장르명
 * @property directorNm : 감독명
 * @property peopleNm : 배우명
 * @property watchGradeNm : 관람등급 명칭
 * @property posterUrl : 포스터 URL
 * @property plot : 영화 줄거리
 */

data class Movie(
    var rank: Int,
    var rankInten: Int,
    var rankOldAndNew: String,
    var name: String,
    var enName: String,
    var openDt: String,
    var audiAcc: String,
    var prdtYear: String,
    var showTm: String,
    var genreNm: List<String>,
    var directorNm: List<String>,
    var peopleNm: List<String>,
    var watchGradeNm: List<String>,
    var posterUrl: String,
    var plot: String
) : Serializable {

    companion object {
        val EMPTY = Movie(
            rank = 0,
            rankInten = 0,
            rankOldAndNew = "",
            name = "",
            enName = "",
            openDt = "",
            audiAcc = "",
            prdtYear = "",
            showTm = "",
            genreNm = emptyList(),
            directorNm = emptyList(),
            peopleNm = emptyList(),
            watchGradeNm = emptyList(),
            posterUrl = "",
            plot = ""
        )
    }
}
