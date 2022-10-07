package com.preonboarding.moviereview.presentation.detailmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.databinding.ItemDetailmovieReviewBinding
import com.preonboarding.moviereview.domain.model.ReviewVo


class ReviewsAdapter : ListAdapter<ReviewVo, ReviewsAdapter.Holder>(ReviewsDiffCallback()) {
    inner class Holder(private val binding: ItemDetailmovieReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewVo) {
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

class ReviewsDiffCallback : DiffUtil.ItemCallback<ReviewVo>() {
    override fun areItemsTheSame(oldItem: ReviewVo, newItem: ReviewVo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ReviewVo, newItem: ReviewVo): Boolean {
        return oldItem == newItem
    }

}
