package com.preonboarding.presentation.view.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.preonboarding.domain.model.MODE
import com.preonboarding.presentation.R
import com.preonboarding.presentation.common.base.BaseFragment
import com.preonboarding.presentation.common.shared.setOnThrottleClickListener
import com.preonboarding.presentation.databinding.FragmentDetailBinding
import com.preonboarding.presentation.view.review.ReviewDialog
import com.preonboarding.presentation.view.review.ReviewValidationDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


const val REVIEW_SUCCESS = "리뷰작성 성공"
const val REVIEW_FAILED = "리뷰작성 실패"

const val DELETE_SUCCESS = "리뷰삭제 성공"
const val DELETE_FAILED = "리뷰삭제 실패"

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            btnReview.setOnThrottleClickListener {
                showReviewDialog()
            }
            btnDelete.setOnThrottleClickListener {
                showValidationDialog(MODE.DELETE)
            }
            btnModify.setOnThrottleClickListener {
                showValidationDialog(MODE.MODIFY)
            }
        }
    }

    private fun showReviewDialog() {
        val dialog = ReviewDialog()
        dialog.show(requireActivity().supportFragmentManager, "ReviewDialog")
    }

    private fun showValidationDialog(mode: MODE) {
        val dialog = ReviewValidationDialog(mode)
        dialog.show(requireActivity().supportFragmentManager, "ValidationDialog")
    }

}