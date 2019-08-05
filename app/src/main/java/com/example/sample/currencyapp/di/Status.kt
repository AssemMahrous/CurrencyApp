/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */

package com.example.sample.currencyapp.di

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    NETWORK_ERROR,
    TOKEN_EXPIRED,
    INTERNAL_SERVER_ERROR,
    NO_DATA
}
