package com.preonboarding.moviereview.presentation.ui.home

import android.os.Bundle
import android.view.View
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentHomeBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.util.NavigationUtil.navigate
import com.preonboarding.moviereview.presentation.common.util.NavigationUtil.navigateUp

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
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