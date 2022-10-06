package com.preonboarding.moviereview.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentSearchBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private val pagingAdapter: SearchMoviePagingAdapter by lazy { SearchMoviePagingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()

        // SearchView 수정 예정
        viewModel.searchMovie("가")

        setRecyclerView()
        observeUiStateFlow()
        observeEventFlow()
    }

    private fun bindViews() {
        binding.tbSearch.setNavigationOnClickListener {
            navigateUp()
        }
    }

    private fun setRecyclerView() {
        binding.rvSearch.apply {
            adapter = pagingAdapter

            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
    }

    private fun observeUiStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                     pagingAdapter.submitData(it)
                }
            }
        }
    }

    private fun observeEventFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(event: UiEvent) = when (event) {
        is UiEvent.ShowError -> Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            event.error,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}