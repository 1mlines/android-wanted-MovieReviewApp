package com.preonboarding.moviereview.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.firebase.database.*
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentDetailBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.const.FIRE_BASE_URL
import com.preonboarding.moviereview.presentation.common.extension.navigate
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    private val detailViewModel : DetailViewModel by viewModels()
    private lateinit var database: DatabaseReference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()


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
                        detailViewModel.searchReviewMovieList()//리뷰 가져오기

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

    companion object {
        private const val TAG = "DetailFragment"
    }
}