package com.loder.movieapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.chip.Chip
import com.loder.movieapp.adapter.SimilarAdapter
import com.loder.movieapp.data.model.DetailMovieModel
import com.loder.movieapp.databinding.ActivityMovieDetailBinding
import com.loder.movieapp.databinding.ChoiceChipBinding
import com.loder.movieapp.mvvm.MovieViewModel
import com.loder.movieapp.mvvm.SimilarViewModel
import com.loder.movieapp.mvvm.VideoViewModel
import com.loder.movieapp.util.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlin.math.roundToInt

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var similarvModel: SimilarViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var similarAdapter: SimilarAdapter
    private lateinit var videoViewModel: VideoViewModel

    private lateinit var id: String
    private lateinit var playerView: PlayerView
    private lateinit var player: ExoPlayer

    private val TAG: String = "MovieDetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("idMovie").toString()
        Log.i(TAG, "id_value:$id")
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.getDetailMovie(id)
        viewModel.observeDetailMovieLiveData().observe(
            this,
        ) {
            fetData(it)
        }

        setDataRecyclerSimilar()
        // getVideo()
        getVideoFromYT()
    }

    private fun fetData(it: DetailMovieModel?) {
        if (it != null) {
            binding.title.text = it.title
            binding.descriptionReadmore.text = it.overview
            binding.descriptionReadmore.visibility = View.VISIBLE
            binding.textReadMore.visibility = View.VISIBLE
            binding.textReadMore.setOnClickListener {
                if (binding.textReadMore.text.toString().equals("Read More")) {
                    binding.descriptionReadmore.maxLines = Integer.MAX_VALUE
                    binding.descriptionReadmore.ellipsize = null
                    binding.textReadMore.text = "Read Less"
                } else {
                    binding.descriptionReadmore.maxLines = 3
                    binding.descriptionReadmore.ellipsize = TextUtils.TruncateAt.END
                    binding.textReadMore.text = "Read More"
                }
            }
            val rate = (it.voteAverage * 100.0).roundToInt() / 100.0
            binding.rating.text = rate.toString()
            binding.anio.text = it.releaseDate.slice(0..3)
            val language = it.originalLanguage.capitalize()
            binding.language.text = language

            val listGenres = it.genres

            for (chip in listGenres) {
                val genreChip = createChip(chip.name)
                binding.chipGroup.addView(genreChip)
            }
        }
    }

    private fun createChip(name: String): Chip {
        val chip = ChoiceChipBinding.inflate(layoutInflater).root
        chip.text = name
        return chip
    }

    fun setDataRecyclerSimilar() {
        recyclerView = binding.similares
        recyclerView.layoutManager = CarouselLayoutManager()

        similarvModel = ViewModelProvider(this)[SimilarViewModel::class.java]
        similarvModel.getSimilar(this.id)
        similarvModel.observeSimilarLiveData().observe(this) {
            similarAdapter = SimilarAdapter(it)
            recyclerView.adapter = similarAdapter
        }
    }

    fun getVideoFromYT() {
        videoViewModel = ViewModelProvider(this)[VideoViewModel::class.java]
        videoViewModel.getVideos(this.id)
        videoViewModel.observeSimilarLiveData().observe(this) {
            val keyVideo = it[it.count() - 1].key
            val videoUrl = Constants.YT_PATH + keyVideo

            val youTubePlayerView: YouTubePlayerView = binding.trailer
            lifecycle.addObserver(youTubePlayerView)
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

                override fun onReady(youTubePlayera: YouTubePlayer) {
                    youTubePlayera.loadVideo(keyVideo, 0F)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState,
                ) {
                    super.onStateChange(youTubePlayer, state)
                }
            })
        }
    }
}
