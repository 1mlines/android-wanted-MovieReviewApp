package com.preonboarding.moviereview.presentation.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.*
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.data.remote.model.ReviewInfo
import com.preonboarding.moviereview.databinding.FragmentDetailBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.const.FIRE_BASE_URL
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import com.preonboarding.moviereview.presentation.common.extension.navigateWithArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    private val detailViewModel : DetailViewModel by viewModels()
    private lateinit var database: DatabaseReference

    private val args by navArgs<DetailFragmentArgs>()
    lateinit var postUrl : String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        setUpViewPager()
        getMovieDetail()
        observeUI()
    }

    private fun observeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                detailViewModel.moviePoster.collect { state ->
                    when(state) {
                        is MoviePosterStatus.Loading -> {
                        }
                        is MoviePosterStatus.Failure -> {}
                        is MoviePosterStatus.Success -> {
                            binding.moviePoster = state.data
                            postUrl=state.data
                        }
                        is MoviePosterStatus.Initial -> {}
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding.btnDetail.setOnClickListener {
            goToReview()
        }

        findReview()//
        binding.layoutHeaderDetail.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }

        binding.layoutHeaderDetail.tbHeader.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_share -> {
                    shareByMessage()
                    true
                }
                else -> false
            }
        }
    }

    private fun findReview(){
        database = FirebaseDatabase.getInstance().reference
        val ref = database.database.getReferenceFromUrl(FIRE_BASE_URL)
        // child 안에 무비 id 가져와야한다.

        ref.child(args.homeData.movieCd).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.value==null){//리뷰가 없을때

                    }
                    else{                        //리뷰가 있을때
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                detailViewModel.reviewList.collectLatest { state ->
                                    when (state) {
                                        is ReviewStatus.Loading -> {

                                        }
                                        is ReviewStatus.Failure -> {

                                        }
                                        is ReviewStatus.Success -> {
                                            val data = state.data//

                                        }
                                        is ReviewStatus.Initial -> {

                                        }
                                    }
                                }
                            }
                        }
                        //TODO: 가져오는 객체가 map이므로 iterator로 뽑아서 -> List에 넣고 -> RecyclerView
//                        HashMap<String, String>().forEach {
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
        val reviewArgs = ReviewInfo(
            movieCd = args.homeData.movieCd,
            movieNm = args.homeData.movieNm,
            postUrl = postUrl,
            rank = args.homeData.rank,
            rankInten = args.homeData.rankInten,
            rankOldAndNew = args.homeData.rankOldAndNew
        )
        navigateWithArgs(DetailFragmentDirections.actionDetailToReview(reviewArgs))
        //navigate(action = R.id.action_detail_to_review)
    }

    private fun setUpViewPager() {
        val tabTitleArray = arrayOf(
            "Details",
            "Reviews",
        )
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tbIndicator, binding.viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private fun getMovieDetail(){
        detailViewModel.fetchMovieDetail(args.homeData.movieCd)
        detailViewModel.setBasicMovieInfo(args.homeData)
        detailViewModel.searchReviewMovieList(args.homeData.movieCd.toInt())
        binding.dailyMovie = args.homeData
    }

    private fun shareByMessage() {
        val sendIntent: Intent = Intent().apply {
            val title = args.homeData.movieNm
            val rank = args.homeData.rank
            val openDate = args.homeData.openDt

            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,
                String.format("제목 : %s\n순위 : %s\n개봉일 : %s", title, rank, openDate))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    companion object {
        private const val TAG = "DetailFragment"
    }
}
