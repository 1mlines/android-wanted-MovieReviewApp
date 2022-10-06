package com.preonboarding.moviereview.presentation.detailmovie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.ActivityDetailmovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var binding: ActivityDetailmovieBinding
    private lateinit var movieCd: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailmovie)
        binding.lifecycleOwner = this

        if (intent != null) {
            val dailyBoxOffice = ""
            movieCd = intent.getStringExtra("movieCd").toString()
        }
        viewModel.getMovieInfo(getString(R.string.kobis_api_key), "20120101")
        viewModel.result.observe(this, Observer { movieInfo ->
            binding.movieInfo = movieInfo
        })

    }
}