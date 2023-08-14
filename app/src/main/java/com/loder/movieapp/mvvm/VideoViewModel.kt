package com.loder.movieapp.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loder.movieapp.data.model.Video
import com.loder.movieapp.data.model.VideoModel
import com.loder.movieapp.data.remote.RetrofitInstance
import com.loder.movieapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoViewModel : ViewModel() {

    private var videoLiveData = MutableLiveData<List<VideoModel>>()
    private val TAG: String = "VideoViewModel"

    fun getVideos(id: String) {
        RetrofitInstance.api.getVideos(id, Constants.KEY).enqueue(object : Callback<Video> {
            override fun onResponse(call: Call<Video>, response: Response<Video>) {
                if (response.body() != null) {
                    videoLiveData.value = response.body()!!.results
                    println(response.body()!!.results)
                } else {
                    Log.e(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<Video>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    fun observeSimilarLiveData(): LiveData<List<VideoModel>> {
        return videoLiveData
    }
}
