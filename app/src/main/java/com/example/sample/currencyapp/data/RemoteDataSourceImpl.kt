package com.example.sample.currencyapp.data


import retrofit2.Retrofit

class RemoteDataSourceImpl(retrofit: Retrofit) : RemoteDataSource {

    override val apiRequests: ApiRequests = retrofit.create(ApiRequests::class.java)
}
