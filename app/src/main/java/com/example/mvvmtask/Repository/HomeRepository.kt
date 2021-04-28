package com.example.mvvmtask.Repository

import com.example.mvvmtask.Network.ApiHelper

class HomeRepository(private val apiHelper: ApiHelper) {

    suspend fun getList() = apiHelper.getList()
}