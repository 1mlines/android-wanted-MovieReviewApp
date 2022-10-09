package com.preonboarding.moviereview.presentation.ui.moviedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.databinding.ItemActorBinding
import com.preonboarding.moviereview.domain.model.MovieInfo

class ActorAdapter : ListAdapter<MovieInfo.Actors, ActorAdapter.ActorViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemActorBinding.inflate(inflater, parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bindItems(getItem(position))
    }

    class ActorViewHolder(private val binding: ItemActorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: MovieInfo.Actors) = with(binding) {
            actor = item
            executePendingBindings()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieInfo.Actors>() {
            override fun areItemsTheSame(
                oldItem: MovieInfo.Actors,
                newItem: MovieInfo.Actors
            ): Boolean {
                return oldItem.actor == newItem.actor
            }

            override fun areContentsTheSame(
                oldItem: MovieInfo.Actors,
                newItem: MovieInfo.Actors
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}