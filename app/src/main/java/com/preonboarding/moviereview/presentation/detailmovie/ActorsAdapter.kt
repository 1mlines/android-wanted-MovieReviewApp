package com.preonboarding.moviereview.presentation.detailmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.data.network.model.kobis.Actor
import com.preonboarding.moviereview.databinding.ItemDetailmovieActorsBinding


class ActorsAdapter : ListAdapter<Actor, ActorsAdapter.Holder>(ActorsDiffCallback()) {
    inner class Holder(private val binding: ItemDetailmovieActorsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Actor) {
            binding.actor = actor
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemDetailmovieActorsBinding.inflate(
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

class ActorsDiffCallback : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }

}
