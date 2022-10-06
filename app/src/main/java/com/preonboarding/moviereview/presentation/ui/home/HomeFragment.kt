package com.preonboarding.moviereview.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie
import com.preonboarding.moviereview.databinding.FragmentHomeBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.const.KOBIS_API_KEY
import com.preonboarding.moviereview.presentation.common.extension.navigate
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import com.preonboarding.moviereview.presentation.common.extension.navigateWithArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()
    private val pagingAdapter: HomePagingAdapter by lazy {
        HomePagingAdapter(
            itemClickListener = {
                //goToDetail()
                navigateWithArgs(HomeFragmentDirections.actionHomeToDetail(
                    it.movieCd.toString()
                ))
                //findNavController().navigate(HomeFragmentDirections.actionHomeToDetail(it.movieCd))

            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        homeViewModel.searchDailyBoxOfficeList()

        homeViewModel.getMovieList(
            key = KOBIS_API_KEY,
            targetDt = "20220103"
        )

        initRecyclerView()
        observeGetMovieList()
    }

    private fun initRecyclerView() {
        binding.rvList.apply {
            adapter = pagingAdapter
        }
    }

    private fun observeGetMovieList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.checkHomeState.collectLatest { movieList ->
                    pagingAdapter.submitData(movieList)
                }
            }
        }
    }


    private fun initListener() = with(binding) {
        btnHome.setOnClickListener {
            goToDetail()
        }

        layoutHeaderHome.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }

        layoutHeaderHome.tbHeader.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_search -> {
                    navigate(R.id.action_home_to_search)
                    true
                }
                else -> false
            }
        }
    }

    private fun goToDetail() {
        navigate(action = R.id.action_home_to_detail)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}