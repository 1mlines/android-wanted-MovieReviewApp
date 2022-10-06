package com.preonboarding.moviereview.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.preonboarding.moviereview.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getDailyBox(getString(R.string.kobis_api_key), "20120101", getString(R.string.seoul_wide_area_code))
        viewModel.getPosterInfo("frozen", getString(R.string.omdb_api_key))
    }
}