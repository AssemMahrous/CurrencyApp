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
    var list: MutableList<CurrencyModel> = ArrayList()

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

        listAdapter = CurrenciesAdapter { i: String, position: Int ->
            //            listAdapter.swapItem(position)

        }
        rv_currencies.adapter = listAdapter

        viewModel.currencies.observe(this, Observer {
            listAdapter.addRates(it)
        })
    }


    fun getData(base: String) {
        viewModel.getRate(base)
    }
}
