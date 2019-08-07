package com.example.sample.currencyapp

import androidx.lifecycle.MutableLiveData
import com.example.sample.currencyapp.base.BaseViewModel
import com.example.sample.currencyapp.data.model.Rates
import com.google.gson.Gson
import io.reactivex.functions.Consumer
import org.json.JSONObject
import javax.inject.Inject


class MainViewModel @Inject constructor() : BaseViewModel<MainRepository>() {
    val rate = MutableLiveData<Any>()

    fun getRate(base: String, to: String) {
        subscribe(repository.getRate(base), Consumer {
            handleValue(it.rates, to)
        })
    }


    private fun handleValue(rates: Rates, to: String) {
        clearSubscription()
        val gson = Gson()
        val json = gson.toJson(rates)
        val obj = JSONObject(json)
        val iter = obj.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            if (key==to){
                rate.postValue(obj.get(key))
                break
            }
        }

        clearSubscription()
    }
}