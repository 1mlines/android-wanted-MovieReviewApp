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
        fun onClick(view: View, position: Int, pw: String)
    }

    interface EditItemClick {
        fun onClick(view: View, position: Int, nickname: String, pw: String)
    }

    var deleteItemClick: DeleteItemClick? = null
    var editItemClick: EditItemClick? = null


    inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: Review) {
            binding.tvNickName.text = items.nickname
            binding.tvContent.text = items.content
            binding.rating.rating = items.rating
            Glide.with(binding.ivReviewImg.context).load(items.imageUri).error(R.drawable.no_img)
                .into(binding.ivReviewImg)
            binding.tvReviewDate.text = items.date

            binding.ivEdit.setOnClickListener {
                var nickname = items.nickname
                var pw = items.password
                editItemClick?.onClick(it, adapterPosition, nickname, pw)
            }
            binding.ivDelete.setOnClickListener {
                var pw = items.password
                deleteItemClick?.onClick(it, adapterPosition, pw)

            }

            //TODO 수정시 validation 확인
            binding.ivEdit.invalidate()
            binding.ivEdit.visibility = View.INVISIBLE

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
