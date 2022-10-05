package com.preonboarding.moviereview.presentation.ui.review

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.data.remote.model.Review
import com.preonboarding.moviereview.databinding.FragmentReviewBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.const.FIRE_BASE_URL
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import com.preonboarding.moviereview.presentation.ui.custom.dialog.gallery.GalleryDialogFragment

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private lateinit var database: DatabaseReference
    private val PERMISSIONS_GALLERY_CODE = 100
    private val PERMISSIONS_CAMERA_CODE = 101
    private var REQUIRED_PERMISSIONS = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    private val reviewViewModel: ReviewViewModel by viewModels()

    fun requestPermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            //설명이 필요한지
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            ) {

                //설명 필요 (사용자가 요청을 거부한 적이 있음)
                ActivityCompat.requestPermissions(requireActivity(),
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_GALLERY_CODE
                )
            } else {

                //설명 필요하지 않음
                ActivityCompat.requestPermissions(requireActivity(),
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_GALLERY_CODE
                )
            }
        } else {
            // 권한 이미 허용됨
            showGalleryDialog()
        }
    }

    // 사용자가 권한 요청 시 호출되는 메소드
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_GALLERY_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허용 되었으면
                    // 갤러리 오픈
                    showGalleryDialog()
                } else {
                    // 권한 거부 되었으면
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        "갤러리 접근 권한 거부됨",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }

    private fun showGalleryDialog() {
        val galleryDialogFragment = GalleryDialogFragment()
        galleryDialogFragment.show(
            childFragmentManager,
            "GalleryDialog"
        )
        galleryDialogFragment.setMyImageClickListener(object :
            GalleryDialogFragment.MyImageClickListener {
            override fun onImageClick(imgUri: Uri) {
                reviewViewModel.reviewImageUri.value = imgUri
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        //detail에서 넘어올때 영화id를 같이 넘겨주세요
        initListener()
        bindingVm()
    }

    private fun initListener() {
        val ref = database.database.getReferenceFromUrl("$FIRE_BASE_URL").child("1").push()

        binding.layoutHeaderReview.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }
        binding.apply {

            btSaveReview.setOnClickListener {
                ref.setValue(Review(
                    content = "아자차",
                    imageUrl = "https://user-images.githubusercontent.com/20774764/152873936-c633b7fb-52f9-4f6b-9cba-895f9e6712ed.jpg",
                    nickName = "현섭이",
                    password = 8888,
                    star = 3.5
                ))
            }

            binding.ivReviewImage.setOnClickListener {
                requestPermission()
            }
        }
    }

    private fun bindingVm() {
        binding.vm = reviewViewModel

        lifecycleScope.launchWhenResumed {
            reviewViewModel.reviewImageUri.collect {
                binding.isReviewImageEmpty = it == Uri.EMPTY
            }
        }
    }

    companion object {
        private const val TAG = "ReviewFragment"
    }
}