package com.loder.movieapp.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loder.movieapp.data.model.DetailMovieModel
import com.loder.movieapp.data.model.MovieModel
import com.loder.movieapp.data.model.Result
import com.loder.movieapp.data.remote.RetrofitInstance
import com.loder.movieapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MovieViewModel : ViewModel() {

    private var movieLiveData = MutableLiveData<List<Result>>()
    private var movieDetailData = MutableLiveData<DetailMovieModel>()
    private val TAG = "VIEWMODEL"

    fun getPopularMovies() {
        RetrofitInstance.api.getPopularMovies(Constants.KEY).enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.body() != null) {
                    movieLiveData.value = response.body()!!.results
                } else {
                    Log.e(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    fun getDetailMovie(id: String) {
        RetrofitInstance.api.getMovieDetail(id, Constants.KEY).enqueue(object : Callback<DetailMovieModel> {
            override fun onResponse(
                call: Call<DetailMovieModel>,
                response: Response<DetailMovieModel>,
            ) {
                if (response.body() != null) {
                    if (response.isSuccessful) {
                        movieDetailData.value = response.body()
                    } else {
                        Log.e(TAG + "Detail" + "noSucessful", response.message())
                    }
                } else {
                    Log.e(TAG + "Detail" + "bodyNull", response.message())
                }
            }

            override fun onFailure(call: Call<DetailMovieModel>, t: Throwable) {
                Log.e(TAG + "Detail" + "Failure", t.message.toString())
            }
        })
    }

    fun observeMovieLiveData(): LiveData<List<Result>> {
        return movieLiveData
    }

    fun observeDetailMovieLiveData(): LiveData<DetailMovieModel> {
        return movieDetailData
    }

}
