/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sample.currencyapp.di

import com.example.sample.currencyapp.BuildConfig
import com.example.sample.currencyapp.data.RemoteDataSource
import com.example.sample.currencyapp.data.RemoteDataSourceImpl
import com.example.sample.currencyapp.utils.RxErrorHandlingCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class, RepositoriesModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        adapterFactory: RxErrorHandlingCallAdapterFactory
    ): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else interceptor.level = HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(adapterFactory)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(retrofit: Retrofit): RemoteDataSource {
        return RemoteDataSourceImpl(retrofit)
    }

    @Singleton
    @Provides
    internal fun provideRxErrorHandlingCallAdapterFactory(): RxErrorHandlingCallAdapterFactory {
        return RxErrorHandlingCallAdapterFactory.create()
    }

}
