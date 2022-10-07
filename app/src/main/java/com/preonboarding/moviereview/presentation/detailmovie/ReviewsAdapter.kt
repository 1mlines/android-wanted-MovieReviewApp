package com.preonboarding.moviereview.presentation.detailmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.databinding.ItemDetailmovieReviewBinding
import com.preonboarding.moviereview.domain.model.ReviewVO


class ReviewsAdapter : ListAdapter<ReviewVO, ReviewsAdapter.Holder>(ReviewsDiffCallback()) {
    inner class Holder(private val binding: ItemDetailmovieReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewVO) {
            binding.review = review
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemDetailmovieReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class ReviewsDiffCallback : DiffUtil.ItemCallback<ReviewVO>() {
    override fun areItemsTheSame(oldItem: ReviewVO, newItem: ReviewVO): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ReviewVO, newItem: ReviewVO): Boolean {
        return oldItem == newItem
    }

}
