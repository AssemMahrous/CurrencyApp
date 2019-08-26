package com.example.sample.currencyapp.data.model

data class RatesResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)