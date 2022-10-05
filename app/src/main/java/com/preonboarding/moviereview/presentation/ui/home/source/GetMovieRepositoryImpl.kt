package com.preonboarding.moviereview.presentation.ui.home.source

import androidx.paging.PagingData
import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieRepositoryImpl @Inject constructor(
    val remote: Remote
) : GetMovieRepository {
    override suspend fun getMovieList(
        key: String,
        targetDt: String
    ): Flow<PagingData<BoxOfficeMovie>> {
        return remote.getMovieList(key, targetDt)
    }
}