package com.example.sample.currencyapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sample.currencyapp.base.BaseViewModel
import com.example.sample.currencyapp.data.model.CurrencyRatePayload
import com.google.gson.Gson
import io.reactivex.functions.Consumer
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


class MainViewModel @Inject constructor() : BaseViewModel<MainRepository>() {
    val currencies = MutableLiveData<List<CurrencyModel>>()
    val rate = MutableLiveData<CurrencyRatePayload>()

    fun getCurrencies() {
        subscribe(repository.getCurrencies(), Consumer {
            val gson = Gson()
            val json = gson.toJson(it)

            val obj = JSONObject(json)
            val iter = obj.keys()
            while (iter.hasNext()) {
                val key = iter.next()
                try {
                    val value = obj.get(key)
                } catch (e: JSONException) {
                    // Something went wrong!
                }

            }
//            currencies.postValue(it)
        })
    }

    fun getRate(value: Int, from: String, to: String) {
        subscribe(repository.getRate(value, from, to), Consumer {
            rate.postValue(it)
        })
    }
}