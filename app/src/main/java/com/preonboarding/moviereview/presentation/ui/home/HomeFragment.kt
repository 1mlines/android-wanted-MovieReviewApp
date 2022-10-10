package com.preonboarding.moviereview.presentation.ui.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentHomeBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.const.KOBIS_API_KEY
import com.preonboarding.moviereview.presentation.common.extension.navigate
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import com.preonboarding.moviereview.presentation.common.extension.navigateWithArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(
            itemClickListener = {
                navigateWithArgs(HomeFragmentDirections.actionHomeToDetail(
                    it
                ))
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initRecyclerView()
        observeGetMovieList()
        getDailyMovie()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDailyMovie() {
        val date: LocalDate = LocalDate.now().minusDays(1)
        val dateFormat = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

        homeViewModel.getDailyMovie(
            key = KOBIS_API_KEY,
            targetDt = dateFormat
        )
    }

    private fun initRecyclerView() {
        binding.rvList.apply {
            adapter = homeAdapter
        }
    }

    private fun observeGetMovieList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.checkHomeState.collectLatest { state ->
                    when(state) {
                        is HomeState.Loading -> {
                            binding.rvList.isVisible = false
                            binding.progressBar.isVisible = true
                        }
                        is HomeState.Failure -> {
                            binding.rvList.isVisible = false
                            binding.progressBar.isVisible = false
                        }
                        is HomeState.Success -> {
                            binding.rvList.isVisible = true
                            binding.progressBar.isVisible = false
                            val data = state.data
                            val boxOfficeResult = data.boxOfficeResult
                            val dailyBoxOfficeList = boxOfficeResult.dailyBoxOfficeList
                            homeAdapter.submitList(dailyBoxOfficeList)
                        }
                        is HomeState.Empty -> {
                            Toast.makeText(requireContext(), "화면이 비었습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun initListener() = with(binding) {

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

    companion object {
        private const val TAG = "HomeFragment"
    }
}


