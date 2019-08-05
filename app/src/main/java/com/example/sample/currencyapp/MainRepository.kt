package com.example.sample.currencyapp

import com.example.sample.currencyapp.base.BaseRepository
import com.example.sample.currencyapp.data.RemoteDataSource
import com.example.sample.currencyapp.data.model.Currencies
import com.example.sample.currencyapp.data.model.CurrencyRatePayload
import io.reactivex.Single
import javax.inject.Inject

class MainRepository @Inject constructor(remoteDataSource: RemoteDataSource) :
    BaseRepository(remoteDataSource) {

    fun getCurrencies(): Single<Currencies> {
        return remoteDataSource.apiRequests.getCurrencies()
    }

    fun getRate(value: Int, from: String, to: String): Single<CurrencyRatePayload> {
        return remoteDataSource.apiRequests.changeRate(value, from, to)
    }
}