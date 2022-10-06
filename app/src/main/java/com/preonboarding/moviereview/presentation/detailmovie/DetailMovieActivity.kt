package com.preonboarding.moviereview.presentation.detailmovie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.data.network.model.kobis.DailyBoxOffice
import com.preonboarding.moviereview.databinding.ActivityDetailmovieBinding
import com.preonboarding.moviereview.domain.model.BoxOffice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var binding: ActivityDetailmovieBinding
    private lateinit var boxOffice: BoxOffice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailmovie)
        binding.lifecycleOwner = this

        if (intent != null) {
            val data = intent.getStringExtra("data")
            if (data != null) {
                boxOffice = Json.decodeFromString<BoxOffice>(data)
                binding.boxOffice = boxOffice
            }
        }

        viewModel.getMovieInfo(boxOffice.movieCd, getString(R.string.kobis_api_key))
        viewModel.movieInfo.observe(this, Observer {
            binding.movieInfo = it
        })
    }
}