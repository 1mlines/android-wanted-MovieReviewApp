package com.preonboarding.moviereview.presentation.ui.review

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentReviewBinding
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import timber.log.Timber

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {
    private val PERMISSIONS_GALLERY_CODE = 100
    private val PERMISSIONS_CAMERA_CODE = 101
    private var REQUIRED_PERMISSIONS = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )


    fun requestPermission(){
        val permissionCheck = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            //설명이 필요한지
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                //설명 필요 (사용자가 요청을 거부한 적이 있음)
                ActivityCompat.requestPermissions(requireActivity(),
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_GALLERY_CODE
                )
            }
            else {

                //설명 필요하지 않음
                ActivityCompat.requestPermissions(requireActivity(),
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_GALLERY_CODE
                )
            }
        }
        else{
            // 권한 이미 허용됨
            getAllImages()
        }
    }

    // 사용자가 권한 요청 시 호출되는 메소드
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSIONS_GALLERY_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허용 되었으면
                    // 갤러리 이미지 가져오기
                    getAllImages()
                }
                else {
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

    private fun getAllImages() {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.layoutHeaderReview.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }
    }

    companion object {
        private const val TAG = "ReviewFragment"
    }
}