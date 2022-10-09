package com.preonboarding.moviereview.presentation.detailmovie

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.ActivityDetailmovieBinding
import com.preonboarding.moviereview.domain.model.BoxOffice
import com.preonboarding.moviereview.domain.model.Reviews
import com.preonboarding.moviereview.presentation.review.ReviewActivity
import com.preonboarding.moviereview.presentation.review.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private val detailMovieViewModel: DetailMovieViewModel by viewModels()
    private lateinit var reviewViewModel: ReviewViewModel

    private lateinit var binding: ActivityDetailmovieBinding
    private lateinit var boxOffice: BoxOffice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailmovie)
        binding.lifecycleOwner = this
        binding.activity = this
        setSupportActionBar(binding.toolbarDetailmovie)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null) {
            val data = intent.getStringExtra("data")
            if (data != null) {
                boxOffice = Json.decodeFromString<BoxOffice>(data)
                binding.boxOffice = boxOffice
            }
        }

        binding.recyclerviewDetailmovieActors.apply {
            adapter = ActorsAdapter()
        }

        binding.recyclerviewDetailmovieDirectors.apply {
            adapter = DirectorsAdapter()
        }

        binding.recyclerviewDetailmovieReviews.apply {
            adapter = ReviewsAdapter()
        }

        detailMovieViewModel.getMovieInfo(boxOffice.movieCd, getString(R.string.kobis_api_key))
        detailMovieViewModel.movieInfo.observe(this, Observer {
            binding.movieInfo = it
            (binding.recyclerviewDetailmovieActors.adapter as ActorsAdapter).submitList(it.actors)
            (binding.recyclerviewDetailmovieDirectors.adapter as DirectorsAdapter).submitList(it.directors)
        })

        reviewViewModel = ViewModelProvider(this)[ReviewViewModel::class.java]
        reviewViewModel.getReviewList(boxOffice.movieCd)
        reviewViewModel.savedReviews.observe(this, Observer {
            val reviews = Reviews(it)
            binding.reviews = reviews
            (binding.recyclerviewDetailmovieReviews.adapter as ReviewsAdapter).submitList(it)
        })
    }

    fun goReviewActivity() {
        val intent = Intent(applicationContext, ReviewActivity::class.java)
        intent.putExtra("movieCd", boxOffice.movieCd)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_deatilmovie, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                super.onOptionsItemSelected(item)
            }
            R.id.item_share -> {
                var shareText = ""
                detailMovieViewModel.movieInfo.observe(this, Observer {
                    var genreToString = ""
                    for (i in it.genres.indices) {
                        genreToString += it.genres[i].genreNm
                        if (i != it.genres.size - 1) {
                            genreToString += "/"
                        }
                    }
                    var directorsToString = ""
                    for (i in it.directors.indices) {
                        directorsToString += it.directors[i].peopleNm
                        if (i != it.directors.size - 1) {
                            directorsToString += "/"
                        }
                    }
                    var actorsToString = ""
                    for (i in it.actors.indices) {
                        actorsToString += it.actors[i].peopleNm
                        if (i != it.actors.size - 1) {
                            actorsToString += "/"
                        }
                    }
                    shareText =
                        "박스오피스 ${boxOffice.ranking}위(${boxOffice.rankInten})${boxOffice.rankType}\n" +
                                "영화명: ${it.movieNm}\n" +
                                "등급: ${it.audits[0].watchGradeNm}\n" +
                                "개봉일: ${it.openDt}\n" +
                                "상영시간(분): ${it.showTm}\n" +
                                "누적관객수(명): ${boxOffice.audiAcc}\n" +
                                "제작연도(년): ${it.prdtYear}\n" +
                                "장르: ${genreToString}\n" +
                                "감독: ${directorsToString}\n" +
                                "출연진: $actorsToString"
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, shareText)
                    startActivity(Intent.createChooser(intent, "Share"))
                })
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}