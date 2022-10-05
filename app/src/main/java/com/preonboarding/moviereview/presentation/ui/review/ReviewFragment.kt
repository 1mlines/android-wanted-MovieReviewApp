package com.preonboarding.moviereview.presentation.ui.review

import android.os.Bundle
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.data.remote.model.Review
import com.preonboarding.moviereview.databinding.FragmentReviewBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.const.FIRE_BASE_URL
import com.preonboarding.moviereview.presentation.common.extension.navigateUp

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {
    private lateinit var database: DatabaseReference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        //detail에서 넘어올때 영화id를 같이 넘겨주세요
        initListener()
    }

    private fun initListener() {
        val ref = database.database.getReferenceFromUrl("$FIRE_BASE_URL").child("1").push()

        binding.layoutHeaderReview.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }
        binding.apply {

            btSaveReview.setOnClickListener{
                ref.setValue(Review(
                    content = "아자차",
                    imageUrl = "https://user-images.githubusercontent.com/20774764/152873936-c633b7fb-52f9-4f6b-9cba-895f9e6712ed.jpg",
                    nickName = "현섭이",
                    password = 8888,
                    star=3.5
                ))
            }
        }
    }


    companion object {
        private const val TAG = "ReviewFragment"
    }
}