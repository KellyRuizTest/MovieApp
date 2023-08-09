package com.loder.movieapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loder.movieapp.MovieDetailActivity
import com.loder.movieapp.R
import com.loder.movieapp.data.model.Result
import com.loder.movieapp.databinding.MovieLayoutBinding
import com.loder.movieapp.util.Constants
import com.squareup.picasso.Picasso

const val TAG = "MovieAdapter"

class MovieAdapter(private val movieList: List<Result>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(Constants.IMAGE_PATH + movieList.get(position).posterPath)
            .placeholder(R.drawable.user_placeholder)
            .resize(200, 240).onlyScaleDown()
            .into(holder.binding.imageMovie)

        holder.binding.nameMovie.text = movieList[position].title

        holder.binding.cardMovie.setOnClickListener {
            val idMovie = movieList.get(position).id.toString()
            val intent = Intent(it.context, MovieDetailActivity::class.java).also {
                it.putExtra("idMovie", idMovie)
            }
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(val binding: MovieLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
