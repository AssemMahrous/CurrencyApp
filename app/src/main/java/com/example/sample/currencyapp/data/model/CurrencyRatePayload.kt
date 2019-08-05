package com.example.sample.currencyapp.data.model

data class CurrencyRatePayload(
    val disclaimer: String,
    val license: String,
    val meta: Meta,
    val request: Request,
    val response: Double
)