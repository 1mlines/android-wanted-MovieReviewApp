package com.preonboarding.moviereview.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.data.remote.model.BoxOfficeMovie
import com.preonboarding.moviereview.databinding.ItemMovieListBinding

class HomePagingAdapter() : PagingDataAdapter<BoxOfficeMovie, HomePagingAdapter.HomeViewHolder>(DIFF_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //return HomeViewHolder(ItemMovieListBinding.inflate(inflater))
        val binding = ItemMovieListBinding.inflate(inflater, parent, false)
        return HomeViewHolder(binding)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }

    }

    inner class HomeViewHolder(
        private val binding: ItemMovieListBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BoxOfficeMovie) {
            binding.dailyMovie = item
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