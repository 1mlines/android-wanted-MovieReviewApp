package com.preonboarding.moviereview.presentation.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentMovieDetailBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import timber.log.Timber

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.e("$args")
    }
}