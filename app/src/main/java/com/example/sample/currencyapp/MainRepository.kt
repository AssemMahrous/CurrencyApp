package com.example.sample.currencyapp

import com.example.sample.currencyapp.base.BaseRepository
import com.example.sample.currencyapp.data.RemoteDataSource
import com.example.sample.currencyapp.data.model.RatesResponse
import io.reactivex.Single
import javax.inject.Inject

class MainRepository @Inject constructor(remoteDataSource: RemoteDataSource) :
    BaseRepository(remoteDataSource) {

    fun getRate(base: String): Single<RatesResponse> {
        return remoteDataSource.apiRequests.changeRate(base)
    }
}