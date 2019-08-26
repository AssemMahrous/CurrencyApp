package com.example.sample.currencyapp

import com.example.sample.currencyapp.base.BaseRepository
import com.google.gson.Gson
import io.reactivex.Single
import javax.inject.Inject

class MainRepository @Inject constructor(
    val gson: Gson
) : BaseRepository() {

    fun getRate(base: String): Single<ArrayList<CurrencyModel>> {
        return remoteDataSource.apiRequests
            .changeRate(base)
            .flatMap { ratesResponse ->
                val currencies = ArrayList<CurrencyModel>()
                currencies.addAll(ratesResponse.rates.map { CurrencyModel(it.key, it.value) })
                return@flatMap Single.just(currencies)
            }
    }
}