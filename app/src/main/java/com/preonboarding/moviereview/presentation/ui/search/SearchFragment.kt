package com.preonboarding.moviereview.presentation.ui.search

import android.os.Bundle
import android.view.View
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentSearchBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.extension.navigateUp

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()

    }

    private fun bindViews() {
        binding.tbSearch.setNavigationOnClickListener {
            navigateUp()
        }
    }

}