package com.loder.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.loder.movieapp.adapter.MovieAdapter
import com.loder.movieapp.adapter.UpcommingRatedAdapter
import com.loder.movieapp.databinding.ActivityMainBinding
import com.loder.movieapp.mvvm.MovieViewModel
import com.loder.movieapp.mvvm.TopRatedViewModel
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: com.loder.movieapp.databinding.ActivityMainBinding
    private lateinit var movieviewModel: MovieViewModel
    private lateinit var topRatedviewModel: TopRatedViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var topRatedAdapter: UpcommingRatedAdapter
    private lateinit var upcommingAdapter: UpcommingRatedAdapter
    private lateinit var movieRecycler: RecyclerView
    private lateinit var topRatedPager: ViewPager2
    private lateinit var upcommingPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataRecyclerView()
        setDataRatedPager()
        // setDataUpcommingPager()
        setUpTransformer()
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

    fun setDataRatedPager() {
        topRatedPager = binding.viewPagerToprated

        topRatedviewModel = ViewModelProvider(this)[TopRatedViewModel::class.java]
        topRatedviewModel.getTopRated()
        topRatedviewModel.observeRatedLiveData().observe(
            this,
        ) {
            topRatedAdapter = UpcommingRatedAdapter(it)
            topRatedPager.adapter = topRatedAdapter
            topRatedPager.offscreenPageLimit = 3
            topRatedPager.clipToPadding = false
            topRatedPager.clipChildren = false
            topRatedPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.95f + r * 0.05f
        }

        topRatedPager.setPageTransformer(transformer)
        // upcommingPager.setPageTransformer(transformer)
    }

    fun setDataUpcommingPager() {
        upcommingPager = binding.viewPagerUpcoming

        topRatedviewModel.getUpcomming()
        topRatedviewModel.observeUpcommingLiveData().observe(
            this,
        ) {
            upcommingAdapter = UpcommingRatedAdapter(it)
            upcommingPager.adapter = upcommingAdapter
            upcommingPager.offscreenPageLimit = 3
            upcommingPager.clipToPadding = false
            upcommingPager.clipChildren = false
            upcommingPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }
}
