package com.preonboarding.moviereview.presentation.ui.review

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.data.remote.model.Review
import com.preonboarding.moviereview.databinding.FragmentReviewBinding
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.presentation.common.base.BaseFragment
import com.preonboarding.moviereview.presentation.common.const.FIRE_BASE_URL
import com.preonboarding.moviereview.presentation.common.extension.navigateUp
import com.preonboarding.moviereview.presentation.ui.custom.dialog.gallery.GalleryDialogFragment

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var database: DatabaseReference
    private lateinit var fbStorage: FirebaseStorage
    private var selectedUri: Uri? = null

    private val reviewViewModel: ReviewViewModel by viewModels()

    private fun showGalleryDialog() {
        val galleryDialogFragment = GalleryDialogFragment()
        galleryDialogFragment.show(
            childFragmentManager,
            "GalleryDialog"
        )
        galleryDialogFragment.setMyImageClickListener(object :
            GalleryDialogFragment.MyImageClickListener {
            override fun onImageClick(image: GalleryImage) {
                reviewViewModel.reviewImageUri.value = image.imgUri
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        fbStorage = FirebaseStorage.getInstance()

        initListener()
        bindingVm()
    }

    private fun initListener() {

        binding.layoutHeaderReview.tbHeader.setNavigationOnClickListener {
            navigateUp()
        }
        binding.apply {
            btSaveReview.setOnClickListener {
                val nickname = editNickNameReview.text.toString()
                val content = editContentReview.text.toString()
                val password = editPasswordReview.text.toString()
                val check = editCheckReview.text.toString()
                val rate = ratingReview.rating
                //val imageUrl = ivReviewImage

                if (nickname == "" || content == "" || password == "") {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.review_null_snack_bar_text),
                        Snackbar.LENGTH_SHORT)
                        .show()
                } else if (password != check) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.review_password_snack_bar_text),
                        Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    // imageUri를 뷰모델에서 관리중이니 String으로 변환해서 저장해주세요.
                    selectedUri = reviewViewModel.reviewImageUri.value
                    showProgress()
                    if (selectedUri != Uri.EMPTY) {
                        val photoUri = selectedUri ?: return@setOnClickListener
                        uploadPhoto(photoUri,
                            successHandler = { uri ->
                                uploadArticle(nickname, content, password.toInt(), uri, rate)
                            },
                            errorHandler = {
                                hideProgress()
                            })
                    } else {
                        // 이미지가 없는 경우 이미지 제외하고 등록
                        uploadArticle(nickname, content, password.toInt(), "", rate)
                    }

                }
            }
        }
        binding.ivReviewImage.setOnClickListener {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }


    private fun uploadPhoto(uri: Uri, successHandler: (String) -> Unit, errorHandler: () -> Unit) {
        val fileName = "${System.currentTimeMillis()}.png" // 이미지 파일 이름
        fbStorage.reference.child("article/photo")
            .child(fileName) // storage 에 article/photo/fileName 경로로 저장
            .putFile(uri)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // 업로드 성공시
                    fbStorage.reference.child("article/photo").child(fileName).downloadUrl
                        .addOnSuccessListener { uri ->
                            // 다운로드 성공시
                            successHandler(uri.toString()) // successHandler 실행
                        }
                        .addOnFailureListener {
                            // 다운로드 실패시
                            errorHandler() // errorHandler 실행
                        }
                } else {
                    // 업로드 실패시
                    errorHandler()
                }
            }
    }

    private fun uploadArticle(
        nickname: String,
        content: String,
        password: Int,
        imageUrl: String,
        rate: Float,
    ) {
        val model = Review(
            content = content,
            imageUrl = imageUrl,
            nickName = nickname,
            password = password,
            star = rate)

        //child에서 detail에서 넘어올때 영화id를 같이 넘겨주세요
        val ref = database.database.getReferenceFromUrl(FIRE_BASE_URL).child("1").push()
        ref.setValue(model)
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            getString(R.string.review_sucess_snack_bar_text),
            Snackbar.LENGTH_SHORT)
            .show()
        navigateUp()
    }

    private fun showProgress() {
        //requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.progressReview.isVisible = true
    }

    private fun hideProgress() {
        //requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        //계속 클릭할때 문제가 생길지도?
        binding.progressReview.isVisible = false
    }


    private fun bindingVm() {
        binding.vm = reviewViewModel

        lifecycleScope.launchWhenResumed {
            reviewViewModel.reviewImageUri.collect {
                binding.isReviewImageEmpty = it == Uri.EMPTY
            }
        }
    }

    private fun initLauncher() {
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var flag = true
            for (entry in it.entries) {
                if (!entry.value) {
                    Snackbar.make(
                        binding.root,
                        "갤러리 접근 권한 거부됨",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    flag = false
                }
            }

            if (flag) showGalleryDialog()
        }
    }

    companion object {
        private const val TAG = "ReviewFragment"
        private val REQUIRED_PERMISSIONS = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}