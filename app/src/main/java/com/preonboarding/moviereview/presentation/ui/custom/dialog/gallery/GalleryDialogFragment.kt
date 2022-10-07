package com.preonboarding.moviereview.presentation.ui.custom.dialog.gallery

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
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
import com.google.android.material.snackbar.Snackbar
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentGalleryDialogBinding
import com.preonboarding.moviereview.domain.model.GalleryImage
import com.preonboarding.moviereview.domain.model.ItemType
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileOutputStream

@AndroidEntryPoint
class GalleryDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentGalleryDialogBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

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
        initLauncher()
    }

    private fun initLauncher() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val bitmap = it.data?.extras?.get("data") as Bitmap
                val uri = saveFile(bitmap)
                galleryViewModel.setCameraImage(uri = uri)
            }
        }

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            when(it) {
                true -> {
                    openCamera()
                    lifecycleScope.launchWhenResumed {
                        galleryViewModel.cameraImage.collect { image ->
                            if(image.imgUri != Uri.EMPTY) {
                                mImageClickListener.onImageClick(galleryViewModel.cameraImage.value)
                                dialog?.dismiss()
                            }
                        }
                    }
                }
                false -> {
                    Snackbar.make(
                        binding.root,
                        "카메라 접근 권한 거부됨",
                        Snackbar.LENGTH_SHORT
                    ).show()
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

        lifecycleScope.launchWhenResumed {
            galleryViewModel.getAllImages().collect {
                galleryPagingAdapter.submitData(it)
            }
        }
    }

    private fun openCamera() {
         val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
         resultLauncher.launch(intent)
    }

    private fun chooseGalleryImage(image: GalleryImage) {
        when(image.type) {

            ItemType.CAMERA -> {
                requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
            }

            ItemType.IMAGE -> {
                mImageClickListener.onImageClick(image)
                dialog?.dismiss()
            }
        }
    }

    @SuppressLint("Recycle")
    fun saveFile(bitmap: Bitmap): Uri {
        val cv = ContentValues()
        cv.put(MediaStore.Images.Media.DISPLAY_NAME, "Movie-${System.currentTimeMillis()}")
        cv.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            cv.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)

        if (uri != null) {
            val scriptor = requireContext().contentResolver.openFileDescriptor(uri, "w")

            if (scriptor != null) {
                val fos = FileOutputStream(scriptor.fileDescriptor)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    cv.clear()
                    cv.put(MediaStore.Images.Media.IS_PENDING, 0)
                    requireContext().contentResolver.update(uri, cv, null, null)
                }
            }
        }

        return uri ?: Uri.EMPTY
    }


    private companion object {
        private const val TAG = "GalleryDialog"
        private const val PERMISSIONS_CAMERA_CODE = 101
        private const val FLAG_CAMERA_CODE = 98
        private const val REQUIRED_PERMISSIONS = Manifest.permission.CAMERA

    }

}