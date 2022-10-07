package com.preonboarding.moviereview.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie
import com.preonboarding.moviereview.databinding.ItemMovieListBinding

class HomeAdapter(
    private val itemClickListener: (BoxOfficeMovie) -> Unit
) : ListAdapter<BoxOfficeMovie, HomeAdapter.HomeViewHolder>(DIFF_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //return HomeViewHolder(ItemMovieListBinding.inflate(inflater))
        val binding = ItemMovieListBinding.inflate(inflater, parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it, itemClickListener) }
    }

    inner class HomeViewHolder(
        private val binding: ItemMovieListBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: BoxOfficeMovie,
            itemClickListener: (BoxOfficeMovie) -> Unit
        ) {
            binding.dailyMovie = item
            binding.root.setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }

    companion object {

        private val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<BoxOfficeMovie>(){

            override fun areItemsTheSame(oldItem: BoxOfficeMovie, newItem: BoxOfficeMovie): Boolean {
                return oldItem.movieNm == newItem.movieNm
            }

            override fun areContentsTheSame(oldItem: BoxOfficeMovie, newItem: BoxOfficeMovie): Boolean {
                return oldItem == newItem
            }
        }
    }
}