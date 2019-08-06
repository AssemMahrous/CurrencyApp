package com.example.sample.currencyapp.data

import com.example.sample.currencyapp.data.model.RatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiRequests {
    @GET("latest")
    fun changeRate(@Query("base") base: String): Single<RatesResponse>
}
