package com.preonboarding.moviereview.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentDetailBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.extension.navigate
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import com.preonboarding.moviereview.presentation.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    private val detailViewModel : DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        detailViewModel.searchReviewMovieList()
    }

    private fun initListener() {
        binding.btnDetail.setOnClickListener {
            goToReview()
        }

        binding.layoutHeaderDetail.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }
    }

    private fun goToReview() {
        navigate(action = R.id.action_detail_to_review)
    }

    companion object {
        private const val TAG = "DetailFragment"
    }
}