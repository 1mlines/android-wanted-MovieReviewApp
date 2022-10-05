package com.preonboarding.moviereview.presentation.ui.custom.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.databinding.ItemGalleryBinding
import com.preonboarding.moviereview.domain.model.GalleryImage

class GalleryAdapter(
    private val onClick: (GalleryImage) -> Unit,
): ListAdapter<GalleryImage, GalleryAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGalleryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(image = it)
        }
    }


    inner class ViewHolder(private val binding: ItemGalleryBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: GalleryImage) {
            binding.apply {
                this.image = image
                this.clItemGallery.setOnClickListener {
                    onClick.invoke(image)
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<GalleryImage>() {
            override fun areItemsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
                oldItem.imgUri == newItem.imgUri

            override fun areContentsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
                oldItem == newItem
        }
    }
}