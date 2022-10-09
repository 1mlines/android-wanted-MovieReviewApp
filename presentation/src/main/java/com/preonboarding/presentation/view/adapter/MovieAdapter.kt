package com.preonboarding.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.domain.model.Movie
import com.preonboarding.presentation.databinding.ItemMovieBinding
import com.preonboarding.presentation.view.list.ListFragmentDirections

class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(diffUtil) {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item

            binding.container.setOnClickListener { view ->
                view.findNavController()
                    .navigate(ListFragmentDirections.actionListFragmentToDetailFragment(item))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
}

