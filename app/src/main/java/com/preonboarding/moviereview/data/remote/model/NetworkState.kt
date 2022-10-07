package com.preonboarding.moviereview.data.remote.model

sealed class NetworkState<T: Any> {
    data class Success<T: Any>(val data: T) : NetworkState<T>()
}
