package com.preonboarding.moviereview.presentation.ui.moviedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.databinding.ItemDirectorBinding
import com.preonboarding.moviereview.domain.model.MovieInfo

class DirectorAdapter : ListAdapter<MovieInfo.Directors, DirectorAdapter.DirectorViewHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDirectorBinding.inflate(inflater, parent, false)
        return DirectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DirectorViewHolder, position: Int) {
        holder.bindItems(getItem(position))
    }

    class DirectorViewHolder(private val binding: ItemDirectorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: MovieInfo.Directors) = with(binding) {
            director = item
            executePendingBindings()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieInfo.Directors>() {
            override fun areItemsTheSame(
                oldItem: MovieInfo.Directors,
                newItem: MovieInfo.Directors
            ): Boolean {
                return oldItem.director == newItem.director
            }

            override fun areContentsTheSame(
                oldItem: MovieInfo.Directors,
                newItem: MovieInfo.Directors
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}