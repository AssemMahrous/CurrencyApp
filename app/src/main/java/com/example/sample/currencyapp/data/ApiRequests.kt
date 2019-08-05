package com.example.sample.currencyapp.data

import com.example.sample.currencyapp.data.model.Currencies
import com.example.sample.currencyapp.data.model.CurrencyRatePayload
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiRequests {

    @GET("currencies.json")
    fun getCurrencies(): Single<Currencies>

    @GET("convert/{value}/{from}/{to}")
    fun changeRate(
        @Path("value") value: Int,
        @Path("from") from: String,
        @Path("to") to: String): Single<CurrencyRatePayload>
}
