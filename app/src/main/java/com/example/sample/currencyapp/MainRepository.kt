package com.example.sample.currencyapp

import com.example.sample.currencyapp.base.BaseRepository
import com.example.sample.currencyapp.utils.EspressoTestingIdlingResource
import io.reactivex.Single
import javax.inject.Inject

class MainRepository @Inject constructor(
) : BaseRepository() {
    val DEFAULT_AMOUNT = 1.0f
    fun getRate(base: String): Single<ArrayList<CurrencyModel>> {
        return remoteDataSource.apiRequests
            .changeRate(base)
            .flatMap { ratesResponse ->
                EspressoTestingIdlingResource.increment()
                val currencies = ArrayList<CurrencyModel>()
                currencies.add(CurrencyModel(base, DEFAULT_AMOUNT))
                currencies.addAll(ratesResponse.rates.map { CurrencyModel(it.key, it.value) })
                return@flatMap Single.just(currencies)
            }
    }
}