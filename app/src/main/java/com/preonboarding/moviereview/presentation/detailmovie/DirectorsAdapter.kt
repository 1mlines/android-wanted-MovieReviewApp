package com.preonboarding.moviereview.presentation.detailmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.data.network.model.kobis.Director
import com.preonboarding.moviereview.databinding.ItemDetailmovieDirectorsBinding


class DirectorsAdapter : ListAdapter<Director, DirectorsAdapter.Holder>(DirectorsDiffCallback()) {
    inner class Holder(private val binding: ItemDetailmovieDirectorsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(director: Director) {
            binding.director = director
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemDetailmovieDirectorsBinding.inflate(
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

class DirectorsDiffCallback : DiffUtil.ItemCallback<Director>() {
    override fun areItemsTheSame(oldItem: Director, newItem: Director): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Director, newItem: Director): Boolean {
        return oldItem == newItem
    }

}
