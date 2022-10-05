package com.preonboarding.moviereview.presentation.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.preonboarding.moviereview.presentation.ui.detail.TabDetailFragment
import com.preonboarding.moviereview.presentation.ui.detail.TabReviewFragment

private const val NUM_TABS = 2

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabDetailFragment()
            1 -> TabReviewFragment()
            else -> TabDetailFragment()
        }
    }
}