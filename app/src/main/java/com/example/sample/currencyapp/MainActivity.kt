package com.example.sample.currencyapp

import android.os.Bundle
import com.example.sample.currencyapp.base.BaseActivity
import com.example.sample.currencyapp.base.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity<MainRepository, MainViewModel>() {

    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun getBaseViewModel() = viewModel
    override fun getBaseViewModelFactory() = viewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrencies()
    }
}
