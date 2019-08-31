package com.example.sample.currencyapp

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.sample.currencyapp.base.BaseActivity
import com.example.sample.currencyapp.base.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity<MainRepository, MainViewModel>() {

    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var listAdapter: CurrenciesAdapter

    override fun getBaseViewModel() = viewModel
    override fun getBaseViewModelFactory() = viewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        listAdapter = CurrenciesAdapter { base: String, value: Float ->
            viewModel.checkRates(base, value)
        }
        rv_currencies.adapter = listAdapter
        viewModel.currencies.observe(this, Observer {
            if (it != null) {
                listAdapter.addRates(it)
                addData()
            }
        })

        viewModel.updateRate.observe(this, Observer {
            listAdapter.updateAmount(it)
        })
        getData()
    }

    fun getData() {
        viewModel.getRate(viewModel.currentBase)
    }

    fun addData() {
    }
}
