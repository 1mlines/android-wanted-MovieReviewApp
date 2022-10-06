package com.preonboarding.moviereview.presentation.ui.custom.dialog.gallery

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.google.android.material.snackbar.Snackbar
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentGalleryDialogBinding
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.model.ItemType
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class GalleryDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentGalleryDialogBinding
    private lateinit var getResult: ActivityResultLauncher<Intent>

    private val galleryViewModel: GalleryDialogViewModel by viewModels()
    private lateinit var galleryPagingAdapter: GalleryPagingAdapter
    private lateinit var mImageClickListener: MyImageClickListener

    interface MyImageClickListener {
        fun onImageClick(image: GalleryImage)
    }

    fun setMyImageClickListener(okBtnClickListener: MyImageClickListener) {
        mImageClickListener = okBtnClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        setStyle(STYLE_NO_TITLE, R.style.GalleryDialogTheme)

        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data?.extras?.get("data") != null) {
                    val uri = getImageUri(
                        requireContext(),
                        it.data?.extras?.get("data") as Bitmap
                    )

                    galleryViewModel.setCameraImage(uri = uri)
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery_dialog, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initRecyclerView()
    }

    private fun initListener() {
        binding.tbGalleryHeader.setNavigationOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initRecyclerView() {
        galleryPagingAdapter = GalleryPagingAdapter( onClick = { chooseGalleryImage(image = it) } )
        binding.rvGallery.adapter = galleryPagingAdapter

        lifecycleScope.launchWhenStarted {
            galleryViewModel.getAllImages().collect {
                galleryPagingAdapter.submitData(it)
            }
        }
    }

    private fun openCamera() {
        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        getResult.launch(intent)
    }

    private fun chooseGalleryImage(image: GalleryImage) {
        when(image.type) {

            ItemType.CAMERA -> {
                if (requestCameraPermission()) {
                    openCamera()
                    if (galleryViewModel.cameraImage.value.imgUri != Uri.EMPTY) {
                        mImageClickListener.onImageClick(galleryViewModel.cameraImage.value)
                        dialog?.dismiss()
                    }
                }
                else {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        "카메라 접근 권한 거부됨",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            ItemType.IMAGE -> {
                mImageClickListener.onImageClick(image)
                dialog?.dismiss()
            }
        }
    }

    private fun requestCameraPermission(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        permission)) {

                    //설명 필요 (사용자가 요청을 거부한 적이 있음)
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        REQUIRED_PERMISSIONS,
                        PERMISSIONS_CAMERA_CODE,
                    )
                }
                else {
                    //설명 필요하지 않음
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        REQUIRED_PERMISSIONS,
                        PERMISSIONS_CAMERA_CODE,
                    )
                }
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSIONS_CAMERA_CODE ->{
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Snackbar.make(
                            requireActivity().findViewById(android.R.id.content),
                            "카메라 접근 권한 거부됨",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return
                    }
                }

                //카메라 호출 메소드
                openCamera()
            }
        }
    }

    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            inImage,
            "Movie-${System.currentTimeMillis()}",
            null
        )
        return Uri.parse(path)
    }

    private companion object {
        private const val TAG = "GalleryDialog"
        private const val PERMISSIONS_CAMERA_CODE = 101
        private const val FLAG_CAMERA_CODE = 98
        private val REQUIRED_PERMISSIONS = arrayOf<String>(
            Manifest.permission.CAMERA,
        )
    }

}