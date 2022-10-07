package com.preonboarding.domain.repository

import com.preonboarding.domain.model.Movie
import com.preonboarding.domain.model.Result

interface MovieRepository {
    suspend fun getBoxOfficeList(): List<Movie>
}