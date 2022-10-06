package com.preonboarding.moviereview.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    val movieListResult: MovieListResult
)

@Serializable
data class MovieListResult(
    @SerialName("totCnt") val totalCount: Int,
    val source: String,
    val movieList: List<Movie>
)

@Serializable
data class Movie(
    @SerialName("movieCd") val movieCode: String,
    @SerialName("movieNm") val movieKrName: String,
    @SerialName("movieNmEn") val movieEnName: String,
    @SerialName("prdtYear") val productionYear: String,
    @SerialName("openDt") val openDateTime: String,
    @SerialName("typeNm") val movieType: String,
    @SerialName("prdtStatNm") val productionState: String,
    @SerialName("nationAlt") val nation: String,
    @SerialName("genreAlt") val genre: String,
    @SerialName("repNationNm") val representNation: String,
    @SerialName("repGenreNm") val representGenre: String,
    val directors: List<Director>,
    @SerialName("companys") val companies: List<Company>
)

@Serializable
data class Director(
    @SerialName("peopleNm") val directorName: String
)

@Serializable
data class Company(
    @SerialName("companyCd") val companyCode: String,
    @SerialName("companyNm") val companyName: String
)