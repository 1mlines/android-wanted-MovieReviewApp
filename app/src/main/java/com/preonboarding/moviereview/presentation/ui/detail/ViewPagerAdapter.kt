package com.preonboarding.moviereview.presentation.ui.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_TABS = 2

class ViewPagerAdapter(fragmentFragment: Fragment) :
    FragmentStateAdapter(fragmentFragment) {

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
