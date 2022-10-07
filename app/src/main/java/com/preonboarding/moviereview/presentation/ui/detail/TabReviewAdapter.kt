package com.preonboarding.moviereview.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.data.remote.model.Review
import com.preonboarding.moviereview.databinding.ItemTabReviewBinding

class TabReviewAdapter
    : ListAdapter<Review, TabReviewAdapter.TabReviewViewHolder>(DIFF_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabReviewViewHolder {
        val binding = ItemTabReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TabReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class TabReviewViewHolder(private val binding: ItemTabReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Review) {
                //TODO: 리뷰 가져오기
                binding.review = item
            }
    }


    companion object {
        private val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<Review>(){

            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.content == newItem.content
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }
}