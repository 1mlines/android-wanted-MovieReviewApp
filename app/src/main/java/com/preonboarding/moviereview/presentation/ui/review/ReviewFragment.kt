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
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentReviewBinding
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import com.preonboarding.moviereview.presentation.ui.custom.dialog.gallery.GalleryDialogFragment

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {
    private val reviewViewModel: ReviewViewModel by viewModels()

    private fun showGalleryDialog() {
        val galleryDialogFragment = GalleryDialogFragment()
        galleryDialogFragment.show(
            childFragmentManager,
            "GalleryDialog"
        )
        galleryDialogFragment.setMyImageClickListener(object: GalleryDialogFragment.MyImageClickListener {
            override fun onImageClick(image: GalleryImage) {
                reviewViewModel.reviewImageUri.value = image.imgUri
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        bindingVm()
    }

    private fun initListener() {
        binding.layoutHeaderReview.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }

        binding.ivReviewImage.setOnClickListener {
            if(requestGalleryPermission()) {
                showGalleryDialog()
            }
            else {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "갤러리 접근 권한 거부됨",
                    Snackbar.LENGTH_SHORT
                ).show()
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


    private fun requestGalleryPermission(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        permission)) {

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
                return false
            }
        }

        return true
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
                for(grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Snackbar.make(
                            requireActivity().findViewById(android.R.id.content),
                            "갤러리 접근 권한 거부됨",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return
                    }
                }

                showGalleryDialog()
            }
        }
    }

    companion object {
        private const val TAG = "ReviewFragment"
        private const val PERMISSIONS_GALLERY_CODE = 100
        private val REQUIRED_PERMISSIONS = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}