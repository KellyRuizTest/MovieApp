package com.loder.movieapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.loder.movieapp.adapter.SimilarAdapter
import com.loder.movieapp.data.model.DetailMovieModel
import com.loder.movieapp.databinding.ActivityMovieDetailBinding
import com.loder.movieapp.mvvm.MovieViewModel
import com.loder.movieapp.mvvm.SimilarViewModel
import com.loder.movieapp.mvvm.VideoViewModel
import com.loder.movieapp.util.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

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

    //private lateinit var youTubePlayerView: YouTubePlayerView

    private val TAG: String = "MovieDetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("idMovie").toString()
        Log.i(TAG, "id_value:$id")
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

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
            binding.anio.text = it.releaseDate.slice(0..3)
        }
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

    /*fun getVideo() {
        videoViewModel = ViewModelProvider(this)[VideoViewModel::class.java]
        videoViewModel.getVideos(this.id)
        videoViewModel.observeSimilarLiveData().observe(this) {

            val keyVideo = it[it.count() - 1].key
            val videoUrl = Constants.YT_PATH + keyVideo
            Log.e(TAG, videoUrl)

            playerView = binding.trailer

            player = ExoPlayer.Builder(this).build()
            playerView.player = player

            val mediaItemm = MediaItem.Builder()
                .setUri(videoUrl)
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .build()

            val mediaSource = ProgressiveMediaSource.Factory(
                DefaultDataSource.Factory(this),
            ).createMediaSource(mediaItemm)

            player.setMediaItem(mediaItemm)
            player.prepare()
            player.play()
        }
    }*/

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
