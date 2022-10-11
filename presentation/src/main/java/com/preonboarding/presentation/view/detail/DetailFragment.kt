package com.preonboarding.presentation.view.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.preonboarding.domain.model.Movie
import com.preonboarding.domain.model.Review
import com.preonboarding.presentation.R
import com.preonboarding.presentation.common.base.BaseFragment
import com.preonboarding.presentation.view.adapter.DetailReviewAdapter
import com.preonboarding.presentation.view.review.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<com.preonboarding.presentation.databinding.FragmentDetailBinding>(R.layout.fragment_detail) {
    private lateinit var phoneUri: Uri
    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null) {
                phoneUri = it.data?.data!!
                tomessage(phoneUri)
            }

        }

    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var reviewAdapter: DetailReviewAdapter
    private lateinit var currentMovieName: String
    private lateinit var actors: List<String>
    private lateinit var reviews: List<Review>
    private lateinit var movieData: Movie
    private var reviewRate: kotlin.Float = 0f


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = detailViewModel
        val args: DetailFragmentArgs by navArgs()
        movieData = args.movie
        currentMovieName = movieData.name
        detailViewModel.setMovieList(currentMovieName)
        detailViewModel.title = currentMovieName
        actors = movieData.peopleNm
        reviewAdapter = DetailReviewAdapter()

        requestPermission()
        initView()
        collectFlow()

    }


    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.reviewUiState.collect { uiState ->
                    if (uiState == ReviewUiState.Review) {
                        showReviewDialog()
                        detailViewModel.initSelectedReview()
                    } else if ((uiState == ReviewUiState.Modify) or (uiState == ReviewUiState.Reading)) {
                        showReviewDialog()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.reviewList.collect { reviewList ->
                    Log.d("리뷰 리스트", "$reviewList")
                    reviews = reviewList
                    var rating = 0.0
                    var count = 0
                    reviewList.map {
                        rating += it.rating
                        count += 1
                    }
                    reviewRate = ((rating / count).toFloat())
                    setReview()
                    Log.d("평점", "${rating / count} ")
                }
            }
        }
    }

    private fun setReview() {
        binding.ratingBar.rating = reviewRate
        reviewAdapter.submitList(reviews)
        binding.rcvReviews.adapter = reviewAdapter
    }

    private fun initView() {

        binding.apply {
            val posterUrl =
                "https://m.media-amazon.com/images/M/MV5BODRmZDVmNzUtZDA4ZC00NjhkLWI2M2UtN2M0ZDIzNDcxYThjL2ltYWdlXkEyXkFqcGdeQXVyNTk0MzMzODA@._V1_SX300.jpg"
            //TODO 포스터 이미지 수정

            Glide.with(ivPoster.context).load(movieData.posterUrl.toUri()).error(R.drawable.no_img)
                .into(ivPoster)
            tvTitle.text = movieData.name
            tvOpenDate.text = "${movieData.openDt} 개봉 (${movieData.prdtYear} 제작)"
            tvRanks.text =
                "순위 : ${movieData.rank} (증감 : ${movieData.rankInten}) / ${movieData.rankOldAndNew}"
            tvAudiCnt.text = "관객수 : ${movieData.audiAcc} 명"
            tvRunningTime.text = "러닝타임 : ${movieData.showTm} 분"
            tvGenre.text = "${movieData.genreNm} / ${movieData.watchGradeNm}"
            tvDirector.text = "감독 : ${movieData.directorNm[0]}"
            tvMovieContent.text = "줄거리 : ${movieData.plot}"
            val actors = "출연 배우 : " + movieData.peopleNm.joinToString(",")
            tvActors.text = actors

            //리뷰 수정시 validation
            reviewAdapter.editItemClick = object : DetailReviewAdapter.EditItemClick {
                override fun onClick(view: View, position: Int, nickname: String, pw: String) {
                    detailViewModel.editState = EditState(true, true)
                    showValidationDialog(MODE.MODIFY)

                }
            }
            //리뷰 삭제
            reviewAdapter.deleteItemClick = object : DetailReviewAdapter.DeleteItemClick {
                override fun onClick(view: View, position: Int, pw: String) {
                    detailViewModel.passwd = pw
                    Timber.e(pw)
                    showValidationDialog(MODE.DELETE)
                }
            }
            //리뷰 추가
            btnAddReview.setOnClickListener {
                detailViewModel.editState = EditState(true, false)
                detailViewModel.changeUiState(ReviewUiState.Review)
            }

            //MMS로 공유
            ivShare.setOnClickListener {
                getPhoneNumber()
            }

        }
    }

    private fun showReviewDialog() {
        val dialog = ReviewDialog()
        dialog.show(requireActivity().supportFragmentManager, "ReviewDialog")
    }

    private fun showValidationDialog(mode: MODE) {
        val dialog = ReviewValidationDialog.newInstance(mode)
        dialog.show(requireActivity().supportFragmentManager, "ValidationDialog")
    }

    private fun requestPermission() {
        val REQUEST_EXTERNAL_STORAGE = 1
        val PERMISSIONS_STORAGE = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val permission = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    private fun getPhoneNumber() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        }
        resultLauncher.launch(intent)

    }

    private fun tomessage(phone: Uri) {
        val projection: Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
        requireActivity().contentResolver.query(phone, projection, null, null, null)
            .use { cursor ->
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        val numberIndex =
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val number = cursor.getString(numberIndex)
                        sendmessage(
                            number,
                            "영화를 추천합니다! [${currentMovieName}] \n ${movieData.openDt} 개봉 \n ${movieData.directorNm} 감독 \n ${movieData.peopleNm[0]} 주연",
                            movieData.posterUrl
                        )
                    }
                }
            }
    }


    private fun sendmessage(phoneNum: String, message: String, posterImgURL: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("mmsto:$phoneNum")
            putExtra("sms_body", message)
            if (posterImgURL.isNotEmpty()) {
                val uri = posterImgURL.toUri()
                putExtra(Intent.EXTRA_STREAM, uri)
            }
        }
        startActivity(intent)
    }

}
