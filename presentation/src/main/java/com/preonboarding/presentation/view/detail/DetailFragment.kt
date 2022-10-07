package com.preonboarding.presentation.view.detail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.preonboarding.presentation.view.review.EditState
import com.preonboarding.presentation.R
import com.preonboarding.presentation.common.base.BaseFragment
import com.preonboarding.presentation.common.shared.setOnThrottleClickListener
import com.preonboarding.presentation.databinding.FragmentDetailBinding
import com.preonboarding.presentation.view.review.MODE
import com.preonboarding.presentation.view.review.ReviewDialog
import com.preonboarding.presentation.view.review.ReviewUiState
import com.preonboarding.presentation.view.review.ReviewValidationDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO 영화 제목 받아서 시작할 때 리스트 받아와야 함
        detailViewModel.setMovieList("헬스천국")
        initView()
        requestPermission()
        collectFlow()
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.reviewUiState.collect { uiState ->
                    if (uiState == ReviewUiState.Review) {
                        showReviewDialog()
                        detailViewModel.initSelectedReview()
                    } else if ((uiState == ReviewUiState.Modify) or (uiState == ReviewUiState.Reading)) {
                        showReviewDialog()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.reviewList.collect { reviewList ->
                    Log.d("리뷰 리스트", "$reviewList")
                    var rating = 0.0
                    var count = 0
                    reviewList.map {
                        rating += it.rating
                        count += 1
                    }
                    Log.d("평점", "${rating / count} ")
                }
            }
        }
    }

    private fun initView() {
        binding.apply {
            btnReview.setOnThrottleClickListener {
                detailViewModel.editState = EditState(true, false)
                detailViewModel.changeUiState(ReviewUiState.Review)
            }
            btnDelete.setOnThrottleClickListener {
                showValidationDialog(MODE.DELETE)
            }
            btnModify.setOnThrottleClickListener {
                detailViewModel.editState = EditState(true, true)
                showValidationDialog(MODE.MODIFY)
            }
            btnReading.setOnClickListener {
                detailViewModel.editState = EditState(false, false)
                detailViewModel.changeUiState(ReviewUiState.Reading)
            }
        }
    }

    private fun showReviewDialog() {
        val dialog = ReviewDialog()
        dialog.show(requireActivity().supportFragmentManager, "ReviewDialog")
    }

    private fun showValidationDialog(mode: MODE) {
        val dialog = ReviewValidationDialog.newInstance(mode)
        dialog.show(requireActivity().supportFragmentManager, "ValidationDialog")
    }

    private fun requestPermission() {
        val REQUEST_EXTERNAL_STORAGE = 1
        val PERMISSIONS_STORAGE = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val permission = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}