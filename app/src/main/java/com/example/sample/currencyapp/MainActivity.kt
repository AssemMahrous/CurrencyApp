package com.example.sample.currencyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.example.sample.currencyapp.base.BaseActivity
import com.example.sample.currencyapp.base.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity<MainRepository, MainViewModel>(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun getBaseViewModel() = viewModel
    override fun getBaseViewModelFactory() = viewModelFactory
    private var rate = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.rate.observe(this, Observer {
            rate = it.toDouble()
            if (tv_currency_input.text.toString() != "") {
                val currency = tv_currency_input.text.toString()
                val result = rate * currency.toDouble()
                setResultText(result.toString())
            }
        })
        initView()
    }

    private fun initView() {
        initSpinnersListener()
        tv_currency_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // pass
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // pass
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    if (rate == 0.0) {
                        doSearch()
                    } else {
                        val result = tv_currency_input.text.toString().toInt() * rate
                        setResultText(result.toString())
                    }
                } else {
                    setResultText("")
                }
            }
        })

    }

    fun setResultText(text: String) {
        tv_result_value.setText(text)
    }

    fun doSearch() {
        if ((sp_from_currency.selectedItemPosition != sp_to_currency.selectedItemPosition)) {
            getData(sp_from_currency.selectedItem.toString(), sp_to_currency.selectedItem.toString())
        }
    }


    private fun initSpinnersListener() {
        sp_from_currency.onItemSelectedListener = this
        sp_to_currency.onItemSelectedListener = this
    }

    fun getData(base: String, to: String) {
        viewModel.getRate(base, to)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // pass
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (sp_from_currency.selectedItemPosition == sp_to_currency.selectedItemPosition) return else rate = 0.0

        if (tv_currency_input.text.toString() != "") doSearch()
    }
}
