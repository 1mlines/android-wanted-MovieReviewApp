package com.preonboarding.presentation.view.review

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.preonboarding.domain.model.MODE
import com.preonboarding.domain.model.ReviewUiState
import com.preonboarding.presentation.R
import com.preonboarding.presentation.common.base.BaseDialogFragment
import com.preonboarding.presentation.databinding.DialogReviewValidataionBinding
import com.preonboarding.presentation.view.detail.DetailViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReviewValidationDialog(private val selectedMode: MODE) :
    BaseDialogFragment<DialogReviewValidataionBinding>(R.layout.dialog_review_validataion) {

    private val reviewViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = super.onCreateView(inflater, container, savedInstanceState)
        initView()
        collectFlow()
        return binding
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reviewViewModel.reviewUiState.collectLatest { uiState ->
                    if (uiState == ReviewUiState.Failure(MODE.VALIDATION)) {
                        binding.tvPasswordError.isVisible = true
                    } else {
                        dialog?.dismiss()
                    }
                }
            }
        }
    }

    private fun initView() {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
        }
        binding.apply {
            btnValidationCheck.setOnClickListener {
                reviewViewModel.checkPasswd(
                    binding.etPasswordCheck.text.toString(),
                    selectedMode
                )
            }
            btnValidationCancel.setOnClickListener {
                dismiss()
            }
        }
    }

}