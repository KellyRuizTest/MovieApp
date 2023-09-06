package com.loder.movieapp.data.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loder.movieapp.data.model.Result

@Dao
interface ResultMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieWatchlist(movie: Result)

    @Query("SELECT * FROM ResultMovie")
    fun getWatchlist(): LiveData<List<Result>>

    @Delete
    fun removeFromWatchlist(movie: Result)
}
