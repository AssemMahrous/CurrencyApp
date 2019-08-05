package com.example.sample.currencyapp.di

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor() {

    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        if (result.value != null)
            result.value = Resource.loading(result.value!!.data)
        else
            result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->
            //            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    AppExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                    }
                    setValue(Resource.success(response.body))
                }

                is ApiErrorResponse -> {
                    onFetchFailed()
                    if (response.status == Status.NETWORK_ERROR)
                        result.value = Resource.error(response.errorMessage!!, null, Status.NETWORK_ERROR)
                    else if (response.status == Status.TOKEN_EXPIRED)
                        result.value = Resource.error(response.errorMessage!!, null, Status.TOKEN_EXPIRED)
                    else result.value = Resource.error(response.errorMessage!!, null)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<RequestType>>
    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
