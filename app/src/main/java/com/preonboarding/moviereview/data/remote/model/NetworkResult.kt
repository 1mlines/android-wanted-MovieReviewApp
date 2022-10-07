package com.preonboarding.moviereview.data.remote.model

sealed class NetworkResult<T: Any> {
    data class Success<T: Any>(val data: T) : NetworkResult<T>()
}
