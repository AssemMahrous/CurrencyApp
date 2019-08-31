package com.example.sample.currencyapp

import com.example.sample.currencyapp.di.AppModule
import com.example.sample.currencyapp.di.Modules
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, AppModule::class,
        Modules::class]
)
interface TestAppComponent {
    fun inject(mainViewTest: MainViewTest)
}