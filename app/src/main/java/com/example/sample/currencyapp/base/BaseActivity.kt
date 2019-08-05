package com.example.sample.currencyapp.base

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.CallSuper
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

        viewModel.loading.observe(this, Observer {
            if (it) showLoading()
            else hideLoading()
        })

        viewModel.error.observe(this, Observer {
            hideLoading()
            showError(it)
        })
    }

    fun showError(it: String?) {
//        Utilities.showAlert(this, it, R.color.colorAccent)
    }


    abstract fun getBaseViewModel(): MBaseViewModel

    abstract fun getBaseViewModelFactory(): ViewModelFactory

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    protected fun hideLoading() {
//        if (::progressDialog.isInitialized) DialogMethods.dismissDialog(this, progressDialog)
    }

    protected fun showLoading() {
//        if (!::progressDialog.isInitialized)
//            progressDialog = DialogMethods
//                    .showProgressDialog(this,
//                            R.string.please_wait,
//                            false,
//                            true)
//        progressDialog.show()
    }


    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
