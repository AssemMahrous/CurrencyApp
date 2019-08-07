package com.example.sample.currencyapp.base

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<repository : BaseRepository,
        MBaseViewModel : BaseViewModel<repository>>
    : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var viewModel: MBaseViewModel

    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
        viewModel = getBaseViewModel()
        viewModelFactory = getBaseViewModelFactory()

        viewModel.Status.observe(this, Observer {
            when (it) {
                is ScreenStatus.Error -> showError(it)
                is ScreenStatus.Loading -> showLoading()
                is ScreenStatus.Loaded -> hideLoading()
                else -> {
                    // todo
                }
            }
        })
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    abstract fun getBaseViewModel(): MBaseViewModel


    abstract fun getBaseViewModelFactory(): ViewModelFactory

    fun showError(error: ScreenStatus.Error) {
        val errorMessage = when {
            error.errorResId != null -> getString(error.errorResId)
            error.errorString != null -> error.errorString
            else -> "Something went wrong"
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    open fun hideLoading() {
        Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show()
    }

    open fun showLoading() {
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
    }
}
