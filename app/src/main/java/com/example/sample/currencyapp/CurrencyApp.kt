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
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import com.example.sample.currencyapp.di.AppInjector
import com.example.sample.currencyapp.di.DaggerAppComponent
import javax.inject.Inject

class CurrencyApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector
    override fun onCreate() {
        super.onCreate()
        context = this
        AppInjector.init(this)
        component = createDaggerComponent()
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



    companion object {
        private lateinit var context: CurrencyApp
        private var component: AppComponent? = null

        fun getContext(): Application {
            return context
        }

        fun getComponent(): AppComponent {
            return component ?: createDaggerComponent()
        }

        fun clearComponent() {
            component = null
        }

        fun createDaggerComponent(): AppComponent {
            return DaggerAppComponent.builder()
                .application(context)
                .context(context.applicationContext)
                .build()
        }
    }
}
