package com.example.sample.currencyapp.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        vm.Status.observe(this, Observer {
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

    abstract fun getBaseViewModel(): MBaseViewModel

    abstract fun getBaseViewModelFactory(): ViewModelFactory

    fun showError(error: ScreenStatus.Error) {
        val errorMessage = when {
            error.errorResId != null -> getString(error.errorResId)
            error.errorString != null -> error.errorString
            else -> "Something went wrong"
        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    open fun hideLoading() {
        Toast.makeText(context, "Loaded", Toast.LENGTH_SHORT).show()
    }

    open fun showLoading() {
        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
    }

}