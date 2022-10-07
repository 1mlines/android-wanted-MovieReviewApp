package com.preonboarding.moviereview.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.preonboarding.moviereview.databinding.ItemSearchResultBinding
import com.preonboarding.moviereview.domain.model.MovieSearchInfo

class SearchMoviePagingAdapter(
    private val onShowDetailClicked: (MovieSearchInfo) -> Unit
): PagingDataAdapter<MovieSearchInfo, SearchMoviePagingAdapter.SearchMovieViewHolder>(
    MOVIE_DIFF_CALLBACK
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchResultBinding.inflate(inflater, parent, false)
        return SearchMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        val item = getItem(position)

        item?.let {
            holder.bindItems(it, onShowDetailClicked)
        }
    }

    class SearchMovieViewHolder(
        private val binding: ItemSearchResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: MovieSearchInfo, onShowDetailClicked: (MovieSearchInfo) -> Unit) {
            with(binding) {
                movie = item

                ivDetail.setOnClickListener {
                    onShowDetailClicked.invoke(item)
                }

                executePendingBindings()
            }
        }
    }

    companion object {
        private val MOVIE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieSearchInfo>() {
            override fun areItemsTheSame(
                oldItem: MovieSearchInfo,
                newItem: MovieSearchInfo
            ) = oldItem.movieCode == newItem.movieCode

            override fun areContentsTheSame(
                oldItem: MovieSearchInfo,
                newItem: MovieSearchInfo
            ) = oldItem == newItem
        }
    }
}