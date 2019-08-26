package com.example.sample.currencyapp

import androidx.lifecycle.MutableLiveData
import com.example.sample.currencyapp.base.BaseViewModel
import io.reactivex.functions.Consumer
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel<MainRepository>() {
    val currencies = MutableLiveData<ArrayList<CurrencyModel>>()
    val updateRate = MutableLiveData<Float>()
    var currentBase = "EUR"
    var DEFAULT_AMOUNT = 1.0f

    fun getRate(baseCurrency: String) {
        currentBase = baseCurrency
        subscribe(repository.getRate(currentBase), Consumer {
            currencies.postValue(it)
        })
    }

    fun checkRates(base: String = currentBase, amount: Float = DEFAULT_AMOUNT) {
        if (base == currentBase) {
            updateRate.postValue(amount)
        } else {
            getRate(base)
        }
    }
}