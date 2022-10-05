package com.preonboarding.moviereview.presentation.ui.custom.dialog.gallery

import android.content.ContentUris
import android.net.Uri
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
import com.preonboarding.moviereview.domain.model.ItemType
import timber.log.Timber

class GalleryDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentGalleryDialogBinding
    private lateinit var galleryAdapter: GalleryAdapter
    private val imageList = mutableListOf<GalleryImage>()

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
        getAllImages()
        initRecyclerView()
    }

    private fun initListener() {
        binding.tbGalleryHeader.setNavigationOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initRecyclerView() {
        galleryAdapter = GalleryAdapter( onClick = { chooseGalleryImage(image = it) } )
        binding.rvGallery.adapter = galleryAdapter
        galleryAdapter.submitList(imageList)
    }

    private fun chooseGalleryImage(image: GalleryImage) {
        when(image.type) {

            ItemType.IMAGE -> {
                Timber.tag(TAG).e("카메라 촬영!")
            }

            ItemType.CAMERA -> {
                mImageClickListener.onImageClick(image)
                dialog?.dismiss()
            }
        }
    }

    private fun getAllImages() {
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID, // 고유 ID
            MediaStore.Images.ImageColumns.DATA, // 파일 경로
            MediaStore.Images.ImageColumns.DISPLAY_NAME, // 이름
            MediaStore.Images.ImageColumns.DATE_ADDED, // 추가된 날짜
        )

        // 가져올 위치를 지정한다. SQL 쿼리 식과 비슷하게 생성
        val selection: String? = null
        val selectionArgs: Array<String>? = null

        val cursor = requireContext().contentResolver.query(
            uriExternal,
            projection,
            selection,
            selectionArgs,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )

        if(cursor != null){
            while(cursor.moveToNext()){

                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val filepath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))

                // 사진 경로 Uri 가져오기
                val uri = ContentUris.withAppendedId(uriExternal, id)

                Timber.tag(TAG).e(uri.toString())
                imageList.add(GalleryImage(
                    id = id,
                    name = name,
                    filePath = filepath,
                    date = date,
                    imgUri = uri,
                    type = ItemType.IMAGE
                ))
            }
            cursor.close()
        }
        imageList.add(0, GalleryImage.cameraItem())
    }

    private companion object {
        private const val TAG = "GalleryDialog"
    }

}