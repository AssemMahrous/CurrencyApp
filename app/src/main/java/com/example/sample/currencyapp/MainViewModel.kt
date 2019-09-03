package com.example.sample.currencyapp

import androidx.lifecycle.MutableLiveData
import com.example.sample.currencyapp.base.BaseViewModel
import com.example.sample.currencyapp.utils.EspressoTestingIdlingResource
import io.reactivex.functions.Consumer
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel<MainRepository>() {
    val currencies = MutableLiveData<ArrayList<CurrencyModel>>()
    val updateRate = MutableLiveData<Float>()
    var currentBase = "EUR"
    var default_amount = 1.0f

    fun getRate(baseCurrency: String) {
        currentBase = baseCurrency
        subscribe(repository.getRate(currentBase), Consumer {
            currencies.postValue(it)
            EspressoTestingIdlingResource.decrement()
        }, Consumer {
            EspressoTestingIdlingResource.decrement()
        })
    }

    fun checkRates(base: String = currentBase, amount: Float = default_amount) {
        if (base == currentBase) {
            updateRate.postValue(amount)
        } else {
            currencies.postValue(null)
            clearSubscription()
            getRate(base)
        }
    }
}