package com.example.sample.currencyapp

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.multidex.MultiDex
import com.example.sample.currencyapp.di.AppComponent
import com.example.sample.currencyapp.di.AppInjector
import com.example.sample.currencyapp.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class CurrencyApp : Application(), HasActivityInjector {
    private lateinit var component: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        component = createDaggerComponent()
    }

    private fun createDaggerComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .application(this)
            .context(applicationContext)
            .build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    @Override
    fun onActivityCreated(activity: Activity, bundle: Bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = Color.WHITE
        }
    }
}
