package com.example.sample.currencyapp

import androidx.lifecycle.MutableLiveData
import com.example.sample.currencyapp.base.BaseViewModel
import io.reactivex.functions.Consumer
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel<MainRepository>() {
    val currencies = MutableLiveData<ArrayList<CurrencyModel>>()

    fun getRate(base: String) {
        subscribe(repository.getRate(base), Consumer {
            currencies.postValue(it)
        })
    }
}