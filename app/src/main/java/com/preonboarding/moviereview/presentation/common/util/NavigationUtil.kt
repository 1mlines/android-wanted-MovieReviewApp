package com.preonboarding.moviereview.presentation.common.util

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

object NavigationUtil {
    fun Fragment.navigate(action: Int) {
        this.findNavController().navigate(action)
    }

    fun Fragment.navigateWithArgs(navDirections: NavDirections) {
        this.findNavController().navigate(navDirections)
    }

    fun Fragment.navigateUp() {
        this.findNavController().navigateUp()
    }
}