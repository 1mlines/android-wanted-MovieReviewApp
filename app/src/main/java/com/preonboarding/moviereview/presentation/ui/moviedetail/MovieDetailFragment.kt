package com.preonboarding.moviereview.presentation.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentMovieDetailBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import com.preonboarding.moviereview.presentation.ui.moviedetail.adapter.ActorAdapter
import com.preonboarding.moviereview.presentation.ui.moviedetail.adapter.DirectorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    private val directorAdapter by lazy { DirectorAdapter() }
    private val actorAdapter by lazy { ActorAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieInfoByMovieCode(args.movie.movieCode)

        bindViews()
        observeMovieInfoUiStateFlow()
        observeMoviePosterUiStateFlow()
    }

    private fun bindViews() {
        binding.tbMovieDetail.setNavigationOnClickListener {
            navigateUp()
        }

        binding.rvDirector.adapter = directorAdapter
        binding.rvActor.adapter = actorAdapter
    }

    private fun observeMovieInfoUiStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieInfoUiState.collect { uiState ->
                    when (uiState) {
                        is MovieDetailUiState.Loading -> {

                        }
                        is MovieDetailUiState.Success -> {
                            Timber.e("${uiState.data}")
                            viewModel.getMoviePosterByMovieName(uiState.data.movieEnName)

                            binding.movieInfo = uiState.data

                            binding.executePendingBindings()
                            directorAdapter.submitList(uiState.data.directors)
                            actorAdapter.submitList(uiState.data.actors)

                        }
                    }
                }
            }
        }
    }


    private fun observeMoviePosterUiStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviePosterUiState.collect { uiState ->
                    when (uiState) {
                        is MovieDetailUiState.Loading -> {

                        }
                        is MovieDetailUiState.Success -> {
                            Timber.e("${uiState.data}")

                            binding.moviePoster = uiState.data.movieImageUrl
                            binding.executePendingBindings()

                        }
                    }
                }
            }
        }

    }
}