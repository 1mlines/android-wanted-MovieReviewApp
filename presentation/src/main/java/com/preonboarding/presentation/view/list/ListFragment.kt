package com.preonboarding.presentation.view.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.preonboarding.presentation.R
import com.preonboarding.presentation.common.base.BaseFragment
import com.preonboarding.presentation.databinding.FragmentListBinding
import com.preonboarding.presentation.view.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMovieList()
        setupAdapter()


        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.rcvMovie.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.rcvMovie.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupAdapter() {
        adapter = MovieAdapter()

        binding.rcvMovie.apply {
            this.adapter = this@ListFragment.adapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.setHasFixedSize(true)
        }
    }
}
