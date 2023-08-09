package com.loder.movieapp.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loder.movieapp.data.model.MovieModel
import com.loder.movieapp.data.model.Result
import com.loder.movieapp.data.remote.RetrofitInstance
import com.loder.movieapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedViewModel : ViewModel() {

    private var topRatedLiveData = MutableLiveData<List<Result>>()
    private var upcommingLiveData = MutableLiveData<List<Result>>()
    private val TAG: String = "TopRatedViewModel"

    fun getTopRated() {
        RetrofitInstance.api.getTopRated(Constants.KEY).enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.body() != null) {
                    topRatedLiveData.value = response.body()!!.results
                } else {
                    Log.e(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    fun getUpcomming() {
        RetrofitInstance.api.getUpcomming(Constants.KEY).enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.body() != null) {
                    upcommingLiveData.value = response.body()!!.results
                } else {
                    Log.e(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    fun observeRatedLiveData(): LiveData<List<Result>> {
        return topRatedLiveData
    }

    fun observeUpcommingLiveData(): LiveData<List<Result>> {
        return upcommingLiveData
    }
}
