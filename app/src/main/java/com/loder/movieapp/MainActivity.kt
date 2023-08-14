package com.loder.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.carousel.CarouselLayoutManager
import com.loder.movieapp.adapter.MovieAdapter
import com.loder.movieapp.adapter.TopRatedAdapter
import com.loder.movieapp.databinding.ActivityMainBinding
import com.loder.movieapp.mvvm.MovieViewModel
import com.loder.movieapp.mvvm.TopRatedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: com.loder.movieapp.databinding.ActivityMainBinding

    // ViewModel
    private lateinit var movieviewModel: MovieViewModel
    private lateinit var topRatedviewModel: TopRatedViewModel

    // Adapters
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var upcommingAdapter: TopRatedAdapter

    // Recyclers
    private lateinit var movieRecycler: RecyclerView
    private lateinit var topRatedRecycler: RecyclerView

    // ViewPager
    private lateinit var topRatedPager: ViewPager2
    private lateinit var upcommingPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataRecyclerView()
        setDataRecyclerRatedPager()
    }

    fun setDataRecyclerView() {
        movieRecycler = binding.movieRecycler
        movieRecycler.layoutManager = GridLayoutManager(applicationContext, 2)
        movieRecycler.setHasFixedSize(true)

        movieviewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieviewModel.getPopularMovies()
        movieviewModel.observeMovieLiveData().observe(
            this,
            Observer {
                movieAdapter = MovieAdapter(it)
                movieRecycler.adapter = movieAdapter
            },
        )
    }

    fun setDataRecyclerRatedPager() {
        topRatedRecycler = binding.viewPagerToprated
        topRatedRecycler.layoutManager = CarouselLayoutManager()

        topRatedviewModel = ViewModelProvider(this)[TopRatedViewModel::class.java]
        topRatedviewModel.getTopRated()
        topRatedviewModel.observeRatedLiveData().observe(this) {
            topRatedAdapter = TopRatedAdapter(it)
            topRatedRecycler.adapter = topRatedAdapter
        }
    }
}
