package com.example.sample.currencyapp

import android.os.Bundle
import androidx.lifecycle.Observer
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

        viewModel.currencies.observe(this, Observer {

        })
        initView()
    }

    private fun initView() {
        getData("EUR")
    }


    fun getData(base: String) {
        viewModel.getRate(base)
    }
}
