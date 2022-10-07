package com.preonboarding.moviereview.presentation.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.ActivityReviewBinding
import com.preonboarding.moviereview.domain.model.ReviewVo

class ReviewActivity : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private lateinit var reviewViewModel: ReviewViewModel
    private var review: ReviewVo? = null
    private lateinit var movieCd: String
    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review)

        val secondIntent = intent
        movieCd = secondIntent.getStringExtra("movieCd").toString()

        db = FirebaseFirestore.getInstance()

        reviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            clickSubmitBtn3()
            finish()
        }
    }

    // 리뷰 작성 Submit 버튼
    private fun clickSubmitBtn3() {

        var r = movieCd?.let {
            ReviewVo(
                binding.etContects.text.toString(),
                "이미지 구현 예정",
                it,
                binding.etName.text.toString(),
                binding.etPw.text.toString(),
                Timestamp.now(),
                binding.ratingbarReview.rating
            )
        }

        if (r != null) {
            reviewViewModel.saveReviewToFirebase(r)
        }
    }
}