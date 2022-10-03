package com.preonboarding.moviereview.presentation.ui.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentReviewBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val TAG = "ReviewFragment"
    }
}