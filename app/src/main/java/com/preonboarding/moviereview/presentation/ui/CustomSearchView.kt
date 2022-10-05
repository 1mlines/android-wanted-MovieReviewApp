package com.preonboarding.moviereview.presentation.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.preonboarding.moviereview.R

class CustomSearchView : SearchView {
    constructor(context: Context) : super(context) {
        initUi(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initUi(context)
    }

    private fun initUi(context: Context) {
        val searchAutoComplete =
            this.findViewById<SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)

        searchAutoComplete.apply {
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setHintTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }
}