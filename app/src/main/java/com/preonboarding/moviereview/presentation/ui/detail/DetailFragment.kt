package com.preonboarding.moviereview.presentation.ui.detail

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentDetailBinding
import com.preonboarding.moviereview.presentation.common.adapter.ViewPagerAdapter
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.extension.navigate
import com.preonboarding.moviereview.presentation.common.extension.navigateUp

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

        //setUpViewPager()

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

    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tbIndicator, binding.viewPager) { tab, position ->
        }.attach()

    }

    companion object {
        private const val TAG = "DetailFragment"
    }
}