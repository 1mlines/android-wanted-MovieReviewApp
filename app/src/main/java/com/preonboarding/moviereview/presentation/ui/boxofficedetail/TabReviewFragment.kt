package com.preonboarding.moviereview.presentation.ui.boxofficedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentTabReviewBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabReviewFragment : BaseFragment<FragmentTabReviewBinding>(R.layout.fragment_tab_review) {

    private val boxofficeDetailViewModel : BoxOfficeDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setListAdapter()
    }

//    private fun setListAdapter() {
//        val tabReviewAdapter = TabReviewAdapter()
//        binding.rvReviewList.adapter = tabReviewAdapter
//        detailViewModel.searchReviewMovieList(1) { review ->
//            tabReviewAdapter.submitList(review)
//        }
//    }
}