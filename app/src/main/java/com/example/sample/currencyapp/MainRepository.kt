package com.example.sample.currencyapp

import com.example.sample.currencyapp.base.BaseRepository
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import org.json.JSONObject
import javax.inject.Inject

class MainRepository @Inject constructor(
//    remoteDataSource: RemoteDataSource, connectivityUtils: ConnectivityUtils,
    val gson: Gson
) : BaseRepository(/*remoteDataSource, connectivityUtils*/) {

    fun getRate(base: String, to1: String): Single<String> {
        return remoteDataSource.apiRequests
            .changeRate(base)
            .flatMap { ratesResponse ->
                val json = gson.toJson(ratesResponse.rates)
                val obj = JSONObject(json)

                obj.keys().forEach { if (it == to1) return@flatMap Single.just(obj.getString(it)) }

                return@flatMap Observable.empty<String>().singleOrError()
            }
    }
}