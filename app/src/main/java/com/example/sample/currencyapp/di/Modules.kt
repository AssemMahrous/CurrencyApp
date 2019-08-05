package com.example.sample.currencyapp.di

import com.example.sample.currencyapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class Modules {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}
