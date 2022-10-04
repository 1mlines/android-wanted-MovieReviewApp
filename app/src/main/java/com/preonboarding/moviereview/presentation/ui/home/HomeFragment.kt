package com.preonboarding.moviereview.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentHomeBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.extension.navigate
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        homeViewModel.searchDailyBoxOfficeList()
    }

    private fun initListener() {
        binding.btnHome.setOnClickListener {
            goToDetail()
        }

        binding.layoutHeaderHome.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }
    }

    private fun goToDetail() {
        navigate(action = R.id.action_home_to_detail)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}