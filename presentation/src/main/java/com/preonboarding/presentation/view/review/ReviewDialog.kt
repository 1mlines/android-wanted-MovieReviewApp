package com.preonboarding.presentation.view.review

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.preonboarding.domain.model.Review
import com.preonboarding.presentation.R
import com.preonboarding.presentation.common.base.BaseDialogFragment
import com.preonboarding.presentation.common.shared.setOnThrottleClickListener
import com.preonboarding.presentation.databinding.DialogReviewBinding
import com.preonboarding.presentation.view.detail.DetailViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class ReviewDialog :
    BaseDialogFragment<DialogReviewBinding>(R.layout.dialog_review) {

    private lateinit var photoUri: Uri
    private val reviewViewModel: DetailViewModel by activityViewModels()
    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                it.data?.data?.let {
                    photoUri = it
                }
                with(photoUri) {
                    binding.ivMovieThumb.setImageURI(this)
                    reviewViewModel.uri = this.toString()
                }
            } else {
                Snackbar.make(
                    requireView(),
                    getString(R.string.cancel_album),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = super.onCreateView(inflater, container, savedInstanceState)
        collectFlow()
        initView()
        return binding
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reviewViewModel.reviewUiState.collect { uiState ->
                    isSuccess(uiState is ReviewUiState.Success)
                }
            }
        }
    }

    private fun isSuccess(state: Boolean) {
        if (state) {
            dialog?.dismiss()
        } else {
            Snackbar.make(
                requireView(),
                getString(R.string.review_error),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
    //TODO 사진을 안넣으면 오류가 발생합니다!
    private fun initView() {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
        }
        binding.apply {
            viewModel = reviewViewModel
            ivReviewBack.setOnClickListener {
                dismiss()
            }
            btnReviewOk.setOnThrottleClickListener {
                if ((binding.etReviewNickname.text.isNotBlank()) and (binding.etReviewPassword.text.length == 6)) {
                    val nowDate =
                        SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()).toString()


                    reviewViewModel.reviewBuffer = Review(
                        etReviewNickname.text.toString(),
                        etReviewPassword.text.toString(),
                        ratingBar.rating,
                        etReviewContent.text.toString(),
                        photoUri.toString(),
                        nowDate
                    )
                    reviewViewModel.uploadReview(reviewViewModel.title)
                } else {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.review_guide),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            ivMovieThumb.setOnClickListener {
                filterActivityLauncher.launch(
                    Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
                )
            }
        }
    }
}