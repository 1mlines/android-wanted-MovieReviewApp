package com.preonboarding.presentation.view.review

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import com.preonboarding.presentation.R
import com.preonboarding.presentation.common.base.BaseDialogFragment
import com.preonboarding.presentation.databinding.DialogReviewBinding
import com.preonboarding.presentation.view.detail.DetailViewModel

class ReviewDialog : BaseDialogFragment<DialogReviewBinding>(R.layout.dialog_review) {

    private val reviewViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = super.onCreateView(inflater, container, savedInstanceState)
        initView()
        return binding
    }

    private fun initView() {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
        }
    }
}