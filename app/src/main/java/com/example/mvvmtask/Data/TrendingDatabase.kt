package com.example.mvvmtask.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exampleproject.responsemodel.TrendingResponseItem

@Database(entities = [TrendingResponseItem::class], version = 1)
abstract class TrendingDatabase : RoomDatabase() {

    abstract fun trendingDao(): TrendingDao
}