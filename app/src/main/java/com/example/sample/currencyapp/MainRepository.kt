package com.example.sample.currencyapp

import com.example.sample.currencyapp.base.BaseRepository
import com.google.gson.Gson
import io.reactivex.Single
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class MainRepository @Inject constructor(
    val gson: Gson
) : BaseRepository() {

    fun getRate(base: String): Single<ArrayList<CurrencyModel>> {
        return remoteDataSource.apiRequests
            .changeRate(base)
            .flatMap { ratesResponse ->
                val currencies = ArrayList<CurrencyModel>()
                val json = gson.toJson(ratesResponse)
                val obj = JSONObject(json)
                val iter = obj.keys()
                while (iter.hasNext()) {
                    val key = iter.next()
                    try {
                        val value = obj.get(key)
                        currencies.add(CurrencyModel(key, value.toString()))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                return@flatMap Single.just(currencies)
            }
    }
}