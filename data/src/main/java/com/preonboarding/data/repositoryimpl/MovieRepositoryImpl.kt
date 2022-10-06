package com.preonboarding.data.repositoryimpl

import com.preonboarding.data.remote.datasource.MovieRemoteDataSource
import com.preonboarding.data.remote.response.boxoffice.DailyBoxOffice
import com.preonboarding.domain.model.Movie
import com.preonboarding.domain.model.Result
import com.preonboarding.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject


// TODO 리팩터링 절실하게 필요 (정호)
class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override suspend fun getBoxOfficeList(): Result<List<Movie>> {

        val result = ArrayList<Movie>()

        withContext(Dispatchers.IO) {
            val responseOfficeListJob = async { movieRemoteDataSource.getBoxOfficeList() }

            lateinit var officeList: List<DailyBoxOffice>

            when (val responseOfficeList = responseOfficeListJob.await()) {
                is Result.Success -> {
                    officeList = responseOfficeList.data.boxOfficeResult.dailyBoxOfficeList
                }
                is Result.Error -> {
                    return@withContext
                }
            }

            for (boxOffice in officeList) {
                val responseMovieDetailJob =
                    async { movieRemoteDataSource.getMovieInfo(boxOffice.movieCd) }

                when (val responseMovieDetail = responseMovieDetailJob.await()) {
                    is Result.Success -> {
                        val detailInfo = responseMovieDetail.data.movieInfoResult.movieInfo

                        val responsePosterJob =
                            async { movieRemoteDataSource.getPoster(detailInfo.movieNmEn) }

                        when (val responsePoster = responsePosterJob.await()) {
                            is Result.Success -> {
                                result.add(
                                    Movie(
                                        rank = boxOffice.rank.toInt(),
                                        rankInten = boxOffice.rankInten.toInt(),
                                        rankOldAndNew = boxOffice.rankOldAndNew,
                                        name = boxOffice.movieNm,
                                        enName = detailInfo.movieNmEn,
                                        openDt = boxOffice.openDt,
                                        audiAcc = boxOffice.audiAcc,
                                        prdtYear = detailInfo.prdtYear,
                                        showTm = detailInfo.showTm,
                                        genreNm = detailInfo.genres.map { it.genreNm },
                                        directorNm = detailInfo.directors.map { it.peopleNm },
                                        peopleNm = detailInfo.actors.map { it.peopleNm },
                                        watchGradeNm = detailInfo.audits.map { it.watchGradeNm },
                                        posterUrl = responsePoster.data.Poster,
                                        plot = responsePoster.data.Plot,
                                    )
                                )
                            }
                            is Result.Error -> {
                                result.add(
                                    Movie(
                                        rank = boxOffice.rank.toInt(),
                                        rankInten = boxOffice.rankInten.toInt(),
                                        rankOldAndNew = boxOffice.rankOldAndNew,
                                        name = boxOffice.movieNm,
                                        enName = detailInfo.movieNmEn,
                                        openDt = boxOffice.openDt,
                                        audiAcc = boxOffice.audiAcc,
                                        prdtYear = detailInfo.prdtYear,
                                        showTm = detailInfo.showTm,
                                        genreNm = detailInfo.genres.map { it.genreNm },
                                        directorNm = detailInfo.directors.map { it.peopleNm },
                                        peopleNm = detailInfo.actors.map { it.peopleNm },
                                        watchGradeNm = detailInfo.audits.map { it.watchGradeNm },
                                        posterUrl = "",
                                        plot = "줄거리 없음",
                                    )
                                )
                            }
                        }
                    }
                    is Result.Error -> {

                    }
                }
            }
        }

        return Result.Success(result)
    }
}
