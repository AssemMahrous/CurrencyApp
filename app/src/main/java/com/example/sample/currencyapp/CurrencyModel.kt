package com.example.sample.currencyapp

class CurrencyModel (val pref: String,
                     val name: String) {
    override fun toString(): String {
        return pref
    }
}