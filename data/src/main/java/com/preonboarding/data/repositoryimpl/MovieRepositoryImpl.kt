package com.preonboarding.data.repositoryimpl

import com.preonboarding.data.remote.datasource.MovieRemoteDataSource
import com.preonboarding.data.remote.response.boxoffice.DailyBoxOffice
import com.preonboarding.di.DispatcherModule
import com.preonboarding.domain.model.Movie
import com.preonboarding.domain.model.Result
import com.preonboarding.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    @DispatcherModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : MovieRepository {
    override suspend fun getBoxOfficeList(): List<Movie> {

        val result = ArrayList<Movie>()

        withContext(dispatcherIO) {
            val responseOfficeListJob = async { movieRemoteDataSource.getBoxOfficeList() }

            val officeList: List<DailyBoxOffice>

            when (val responseOfficeList = responseOfficeListJob.await()) {
                is Result.Success -> {
                    officeList = responseOfficeList.data.boxOfficeResult.dailyBoxOfficeList
                }
                is Result.Error -> {
                    return@withContext
                }
            }

            for (boxOffice in officeList) {
                launch {
                    val tmp = Movie.EMPTY.copy()

                    val responseMovieDetailJob =
                        async { movieRemoteDataSource.getMovieInfo(boxOffice.movieCd) }

                    when (val responseMovieDetail = responseMovieDetailJob.await()) {
                        is Result.Success -> {
                            val detailInfo = responseMovieDetail.data.movieInfoResult.movieInfo

                            tmp.apply {
                                rank = boxOffice.rank.toInt()
                                rankInten = boxOffice.rankInten.toInt()
                                rankOldAndNew = boxOffice.rankOldAndNew
                                name = boxOffice.movieNm
                                enName = detailInfo.movieNmEn
                                openDt = boxOffice.openDt
                                audiAcc = boxOffice.audiAcc
                                prdtYear = detailInfo.prdtYear
                                showTm = detailInfo.showTm
                                genreNm = detailInfo.genres.map { it.genreNm }
                                directorNm = detailInfo.directors.map { it.peopleNm }
                                peopleNm = detailInfo.actors.map { it.peopleNm }
                                watchGradeNm = detailInfo.audits.map { it.watchGradeNm }
                            }

                            val responsePosterJob =
                                async {
                                    movieRemoteDataSource.getPoster(
                                        detailInfo.movieNmEn,
                                        detailInfo.prdtYear
                                    )
                                }

                            when (val responsePoster = responsePosterJob.await()) {
                                is Result.Success -> {
                                    tmp.apply {
                                        var poster = responsePoster.data.Poster

                                        if (poster == "N/A") {
                                            poster = ""
                                        }

                                        posterUrl = poster
                                        plot = responsePoster.data.Plot
                                    }
                                }
                                is Result.Error -> {}
                            }
                        }
                        is Result.Error -> {}
                    }
                    result.add(tmp)
                }
            }
        }

        return result.sortedBy { it.rank }
    }
}
