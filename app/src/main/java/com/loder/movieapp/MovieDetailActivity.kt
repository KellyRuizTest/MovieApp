package com.loder.movieapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.loder.movieapp.data.model.DetailMovieModel
import com.loder.movieapp.databinding.ActivityMovieDetailBinding
import com.loder.movieapp.mvvm.MovieViewModel

const val TAG = "MovieDetailActivity"

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("idMovie")
        Log.i(TAG, "id_value:$id")
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        if (id != null) {
            viewModel.getDetailMovie(id)
            viewModel.observeDetailMovieLiveData().observe(
                this,
            ) {
                fetData(it)
            }
        }
    }

    private fun fetData(it: DetailMovieModel?) {
        if (it != null) {
            binding.title.text = it.title.toString()
            binding.description.text = it.overview
        }
    }
}
