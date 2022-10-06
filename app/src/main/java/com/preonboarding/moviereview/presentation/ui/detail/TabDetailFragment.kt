package com.preonboarding.moviereview.presentation.ui.detail

import android.os.Bundle
import android.view.View
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentDetailBinding
import com.preonboarding.moviereview.databinding.FragmentTabDetailBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabDetailFragment : BaseFragment<FragmentTabDetailBinding>(R.layout.fragment_tab_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}