package com.example.sample.currencyapp.base

import com.example.sample.currencyapp.data.RemoteDataSource
import com.example.sample.currencyapp.di.ConnectivityUtils
import javax.inject.Inject

//open class BaseRepository @Inject constructor(
//    val remoteDataSource: RemoteDataSource,
//    val connectivityUtils: ConnectivityUtils)


open class BaseRepository {
    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @Inject
    lateinit var connectivityUtils: ConnectivityUtils
}