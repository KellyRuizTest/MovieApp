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

class SimilarViewModel : ViewModel() {

    private var similarLiveData = MutableLiveData<List<Result>>()
    private val TAG: String = "SimilarViewModel"

    fun getSimilar(id: String) {
        RetrofitInstance.api.getSimilar(id, Constants.KEY).enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.body() != null) {
                    similarLiveData.value = response.body()!!.results
                } else {
                    Log.e(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    fun observeSimilarLiveData(): LiveData<List<Result>> {
        return similarLiveData
    }
}
