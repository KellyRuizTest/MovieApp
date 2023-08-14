package com.loder.movieapp.data.remote

import com.loder.movieapp.data.model.DetailMovieModel
import com.loder.movieapp.data.model.MovieModel
import com.loder.movieapp.data.model.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("popular")
    fun getPopularMovies(@Query("api_key") api_key: String): Call<MovieModel>

    @GET("{id}")
    fun getMovieDetail(
        @Path(value = "id") id: String,
        @Query("api_key") api_key: String,
    ): Call<DetailMovieModel>

    @GET("top_rated")
    fun getTopRated(@Query("api_key") api_key: String): Call<MovieModel>

    @GET("upcoming")
    fun getUpcomming(@Query("api_key") api_key: String): Call<MovieModel>

    @GET("{id}/similar")
    fun getSimilar(
        @Path(value = "id") id: String,
        @Query("api_key") api_key: String,
    ): Call<MovieModel>

    @GET("{id}/videos")
    fun getVideos(
        @Path(value = "id") id: String,
        @Query("api_key") api_key: String,
    ): Call<Video>
}
