package com.preonboarding.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.preonboarding.domain.model.Review
import com.preonboarding.presentation.R
import com.preonboarding.presentation.databinding.ItemReviewBinding

//리뷰 아이템


class DetailReviewAdapter() : ListAdapter<Review, DetailReviewAdapter.ReviewViewHolder>(diffUtil) {

    interface DeleteItemClick {
        fun onClick(view: View, position: Int)
    }

    interface EditItemClick {
        fun onClick(view: View, position: Int)
    }

    var deleteItemClick: DeleteItemClick? = null
    var editItemClick: EditItemClick? = null



    inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: Review) {
            binding.ivReviewImg
            binding.tvNickName.text = items.nickname
            binding.tvContent.text = items.content
            binding.rating.rating = items.rating
            Glide.with(binding.ivEdit.context).load(items.imageUri).error(R.drawable.no_img)
                .into(binding.ivReviewImg)
            binding.tvReviewDate.text = items.date

            binding.ivEdit.setOnClickListener {
                editItemClick?.onClick(it,adapterPosition)
            }
            binding.ivDelete.setOnClickListener {
                deleteItemClick?.onClick(it,adapterPosition)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(currentList[position])

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(
                oldItem: Review,
                newItem: Review
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: Review,
                newItem: Review
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}
