package com.example.sample.currencyapp.data.model

data class Request(
    val amount: Double,
    val from: String,
    val query: String,
    val to: String
)