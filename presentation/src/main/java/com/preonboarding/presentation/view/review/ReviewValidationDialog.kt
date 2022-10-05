package com.preonboarding.presentation.view.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.preonboarding.presentation.R
import com.preonboarding.presentation.common.base.BaseDialogFragment
import com.preonboarding.presentation.databinding.DialogReviewValidataionBinding

class ReviewValidationDialog :
    BaseDialogFragment<DialogReviewValidataionBinding>(R.layout.dialog_review_validataion) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = super.onCreateView(inflater, container, savedInstanceState)
        return binding
    }

}