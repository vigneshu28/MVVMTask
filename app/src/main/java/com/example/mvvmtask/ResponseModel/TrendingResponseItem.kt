package com.example.exampleproject.responsemodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_list")
data class TrendingResponseItem(
    @PrimaryKey val author: String,
    val avatar: String,
    val currentPeriodStars: Int,
    val description: String,
    val forks: Int,
    val language: String,
    val languageColor: String,
    val name: String,
    val stars: Int,
    val url: String
)