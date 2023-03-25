package com.omarahmed.movies.features.movies.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omarahmed.movies.R
import com.omarahmed.movies.core.Constants
import com.omarahmed.movies.databinding.MoviesListItemBinding
import com.omarahmed.movies.features.movies.data.models.Movie

class MoviesRecyclerViewAdapter(
    private val onMovieClicked: (Int) -> Unit,
) :
    ListAdapter<Movie, MoviesRecyclerViewAdapter.MoviesViewHolder>(MyDiffUtil) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MoviesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener { onMovieClicked(movie.id) }
        holder.bind(movie)
    }

    inner class MoviesViewHolder(private val itemBinding: MoviesListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movie: Movie) = with(itemBinding) {
            movieTitleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate
            ratingTextView.text = movie.voteAverage.toFloat().toString()
            // I got the rating in 10 and I want to show it in 5
            movieRatingBar.rating = movie.voteAverage.toFloat() / 10 * 5
            Glide.with(itemView.context)
                .load("${Constants.IMAGES_URL}${Constants.IMAGES_SIZE}${movie.posterPath}")
                .placeholder(R.drawable.placeholder_ic)
                .into(movieImageView)
        }

    }
}