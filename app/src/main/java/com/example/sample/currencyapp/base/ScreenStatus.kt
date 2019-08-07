package com.example.sample.currencyapp.base

import androidx.annotation.StringRes

sealed class ScreenStatus {
    data class Error(
        val throwable: Throwable? = null,
        val errorString: String? = null,
        @StringRes val errorResId: Int? = null
    ) : ScreenStatus()

    data class Success<T>(val t: T)

    class Loading() : ScreenStatus()

    class Loaded() : ScreenStatus()
}