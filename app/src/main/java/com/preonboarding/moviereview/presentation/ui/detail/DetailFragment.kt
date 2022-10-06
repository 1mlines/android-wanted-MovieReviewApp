package com.preonboarding.moviereview.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.*
import com.google.android.material.tabs.TabLayoutMediator
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentDetailBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.const.FIRE_BASE_URL
import com.preonboarding.moviereview.presentation.common.extension.navigate
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    private val detailViewModel : DetailViewModel by viewModels()
    private lateinit var database: DatabaseReference
    val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        setUpViewPager()
        //checkMovieCd()

    }

    private fun initListener() {
        binding.btnDetail.setOnClickListener {
            goToReview()
        }

        findReview()//

        binding.layoutHeaderDetail.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }
    }

    private fun findReview(){
        database = FirebaseDatabase.getInstance().reference
        val ref = database.database.getReferenceFromUrl(FIRE_BASE_URL)
        // child 안에 무비 id 가져와야한다.

        ref.child("1").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.value==null){//리뷰가 없을때

                    }
                    else{                        //리뷰가 있을때
                        val review = detailViewModel.searchReviewMovieList(1)//리뷰 가져오기
                        //TODO: 가져오는 객체가 map이므로 iterator로 뽑아서 -> List에 넣고 -> RecyclerView
//                        map.forEach {
//
//                        }

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            }
        )
    }

    private fun goToReview() {
        navigate(action = R.id.action_detail_to_review)
    }

    private fun setUpViewPager() {
        val tabTitleArray = arrayOf(
            "Details",
            "Reviews",
        )
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tbIndicator, binding.viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private fun checkMovieCd(){
        binding.tvTest.text = args.homeData
    }

    companion object {
        private const val TAG = "DetailFragment"
    }
}