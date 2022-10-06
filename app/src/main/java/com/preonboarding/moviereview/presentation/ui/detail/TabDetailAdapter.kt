package com.preonboarding.moviereview.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.data.remote.model.Directors
import com.preonboarding.moviereview.data.remote.model.Review
import com.preonboarding.moviereview.databinding.ItemDirectorActorBinding

class TabDetailAdapter
    : ListAdapter<Directors, TabDetailAdapter.TabDetailViewHolder>(TabDetailAdapter.DIFF_COMPARATOR){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabDetailViewHolder {
        val binding = ItemDirectorActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TabDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class TabDetailViewHolder(private val binding: ItemDirectorActorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Directors) {
            //TODO: 감독 가져오기
            //binding. = item
        }
    }

    companion object {
        private val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<Directors>(){

            override fun areItemsTheSame(oldItem: Directors, newItem: Directors): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Directors, newItem: Directors): Boolean {
                return oldItem == newItem
            }
        }
    }
}