package com.example.sample.currencyapp.base

import com.example.sample.currencyapp.data.RemoteDataSource
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    val remoteDataSource: RemoteDataSource
) {

    fun getRemoteService(): RemoteDataSource {
        return remoteDataSource
    }
}