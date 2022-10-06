package com.preonboarding.data.common.constant

import android.annotation.SuppressLint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
private val dateFormat = SimpleDateFormat("yyyyMMdd")

fun getCurrentDate(): String {
    return dateFormat.format(Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L))
}
