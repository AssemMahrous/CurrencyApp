package com.example.sample.currencyapp.data

import com.example.sample.currencyapp.data.model.Currencies
import com.example.sample.currencyapp.data.model.CurrencyRatePayload
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Mostafa on 08/20/2017.
 */

interface ApiRequests {

    @GET("currencies.json")
    fun currencies(): Single<Currencies>

    @GET("convert/{value}/{from}/{to}")
    fun changeRate(
        @Path("value") value: String,
        @Path("from") from: String,
        @Path("to") to: String): Single<CurrencyRatePayload>
}
