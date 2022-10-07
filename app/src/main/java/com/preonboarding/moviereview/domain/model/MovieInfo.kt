package com.preonboarding.moviereview.domain.model


data class MovieInfo(
    val movieCode: String,
    val movieKrName: String,
    val movieEnName: String,
    val movieRunTime: String,
    val productionYear: String,
    val openDateTime: String,
    val genres: List<Genres>,
    val directors: List<Directors>,
    val actors: List<Actors>,
    val audits: String
) {
    data class Genres(
        val genreName: String
    )

    data class Directors(
        val director: String
    )

    data class Actors(
        val actor: String,
        val castRole: String
    )
}
