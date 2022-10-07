package com.preonboarding.moviereview.presentation.ui.boxofficedetail

import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie

sealed class MovieBasicStatus {
    object Initial: MovieBasicStatus()
    class Main(val data: BoxOfficeMovie) : MovieBasicStatus()
}
