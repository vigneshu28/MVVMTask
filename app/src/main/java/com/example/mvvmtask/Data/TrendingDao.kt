package com.example.mvvmtask.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exampleproject.responsemodel.TrendingResponseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingDao {
    @Query("SELECT * FROM trending_list")
    fun getAllTrendingList(): Flow<List<TrendingResponseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingList(restaurants: List<TrendingResponseItem>)

    @Query("DELETE FROM trending_list")
    suspend fun deleteTrendingList()
}