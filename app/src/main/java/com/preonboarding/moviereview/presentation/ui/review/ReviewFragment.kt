package com.preonboarding.moviereview.presentation.ui.review

import android.os.Bundle
import android.view.View
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentReviewBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.extension.navigateUp

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.layoutHeaderReview.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }
    }


    companion object {
        private const val TAG = "ReviewFragment"
    }
}