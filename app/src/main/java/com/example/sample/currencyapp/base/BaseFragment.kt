package com.example.sample.currencyapp.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.sample.currencyapp.R
import com.example.sample.currencyapp.di.Injectable


abstract class BaseFragment<repository : BaseRepository, MBaseViewModel : BaseViewModel<repository>>
    : Fragment(), Injectable {

    private lateinit var vm: MBaseViewModel
    private lateinit var progressDialog: ProgressDialog
    private lateinit var vmf: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm = getBaseViewModel()
        vmf = getBaseViewModelFactory()

        super.onViewCreated(view, savedInstanceState)
        vm.loading.observe(this, Observer {
            if (it) showLoading()
            else hideLoading()
        })

        vm.error.observe(this, Observer {
            hideLoading()
            showError(it)
        })
    }

    fun showError(it: String?) {
//        Utilities.showAlert(activity, it, R.color.colorAccent)
    }

    abstract fun getBaseViewModel(): MBaseViewModel

    abstract fun getBaseViewModelFactory(): ViewModelFactory

    open fun hideLoading() {
//        if (::progressDialog.isInitialized) DialogMethods.dismissDialog(activity, progressDialog)
    }

    open fun showLoading() {
//        if (!::progressDialog.isInitialized)
//            progressDialog = DialogMethods
//                    .showProgressDialog(activity,
//                            R.string.please_wait,
//                            false,
//                            true)
//        progressDialog.show()
    }

}