package com.preonboarding.moviereview.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentTabDetailBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TabDetailFragment : BaseFragment<FragmentTabDetailBinding>(R.layout.fragment_tab_detail) {

    val args by navArgs<DetailFragmentArgs>()

    private val detailViewModel: DetailViewModel by viewModels({requireParentFragment()})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMovieInfo()
    }

    private fun setMovieInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                detailViewModel.movieInfo.collect { state ->
                    when(state) {
                        is MovieStatus.Loading -> {}
                        is MovieStatus.Failure -> {}
                        is MovieStatus.Success -> {
                            binding.movieInfo = state.data.movieInfoResult.movieInfo
                            detailViewModel.fetchPoster(state.data.movieInfoResult.movieInfo.movieNmEn)
                        }
                        is MovieStatus.Initial -> {}
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                detailViewModel.basicInfo.collect { state ->
                    when(state) {
                        is MovieBasicStatus.Initial -> {
                        }
                        is MovieBasicStatus.Main -> {
                            binding.dailyMovie = state.data
                        }
                    }
                }
            }
        }
    }
}
