package com.preonboarding.moviereview.presentation.ui.home.source

import androidx.paging.PagingData
import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie
import kotlinx.coroutines.flow.Flow

interface GetMovieRepository {

    suspend fun getMovieList(key: String, targetDt: String): Flow<PagingData<BoxOfficeMovie>>

}