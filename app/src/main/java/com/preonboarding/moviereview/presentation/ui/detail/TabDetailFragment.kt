package com.preonboarding.moviereview.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentDetailBinding
import com.preonboarding.moviereview.databinding.FragmentTabDetailBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabDetailFragment : BaseFragment<FragmentTabDetailBinding>(R.layout.fragment_tab_detail) {

    private val detailViewModel : DetailViewModel by viewModels()
    val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setMovieInfo()
    }

//    private fun setMovieInfo() {
//        detailViewModel.fetchMovieDetail(movieCd = args.movieCd)
//        lifecycleScope.launchWhenStarted {
//            detailViewModel.movieInfo.collect {
//                binding.movielInfo = it
//            }
//        }
//    }
}