package com.example.sample.currencyapp

import com.example.sample.currencyapp.base.BaseRepository
import com.example.sample.currencyapp.data.RemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class MainRepository @Inject constructor( remoteDataSource: RemoteDataSource) :
        BaseRepository( remoteDataSource) {


}