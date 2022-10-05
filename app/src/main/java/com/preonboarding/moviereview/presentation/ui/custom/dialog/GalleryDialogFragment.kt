package com.preonboarding.moviereview.presentation.ui.custom.dialog

import android.content.ContentResolver
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.preonboarding.moviereview.R
import com.preonboarding.moviereview.databinding.FragmentGalleryDialogBinding
import com.preonboarding.moviereview.domain.model.GalleryImage
import timber.log.Timber

class GalleryDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentGalleryDialogBinding
    private lateinit var galleryAdapter: GalleryAdapter
    private val imageList = mutableListOf<GalleryImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
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
        getAllImages()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        galleryAdapter = GalleryAdapter( onClick = { chooseGalleryImage(imgUri = it.imgUri) } )
        binding.rvGallery.adapter = galleryAdapter
        galleryAdapter.submitList(imageList)
    }

    private fun chooseGalleryImage(imgUri: String) {
        Timber.tag(TAG).e(imgUri)
    }

    fun getAllImages() {
        val cursor = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )

        if(cursor != null){
            while(cursor.moveToNext()){
                // 사진 경로 Uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                imageList.add(GalleryImage(imgUri = uri))
            }
            cursor.close()
        }
    }

    private companion object {
        private const val TAG = "GalleryDialog"
    }

}