package com.example.sample.currencyapp

import androidx.lifecycle.MutableLiveData
import com.example.sample.currencyapp.base.BaseViewModel
import io.reactivex.functions.Consumer
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel<MainRepository>() {
    val rate = MutableLiveData<String>()

    fun getRate(base: String, to: String) {
        subscribe(repository.getRate(base, to), Consumer {
            handleData(it)
            clearSubscription()
        })
    }

    private fun handleData(data: String) {
        rate.postValue(data)
    }
}