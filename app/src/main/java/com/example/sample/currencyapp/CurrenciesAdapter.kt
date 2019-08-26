package com.example.sample.currencyapp

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currencies_signle_item.view.*
import java.util.*

class CurrenciesAdapter(
    val listener: (id: String, value: Float) -> Unit
) : RecyclerView.Adapter<CurrenciesAdapter.CurrenciesHolder>() {
    var data = ArrayList<CurrencyModel>()
    private var amount = 1.0F

    fun addRates(currencies: ArrayList<CurrencyModel>) {
        if (data.isEmpty()) {
            data.addAll(currencies)
        }
        for (currency in currencies) {
            data.forEachIndexed { index, _ ->
                if (data[index].symbol == currency.symbol)
                    data[index].rate = currency.rate
            }
        }
        notifyItemRangeChanged(0, data.size - 1, amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesHolder {
        return CurrenciesHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.currencies_signle_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CurrenciesHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class CurrenciesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var base: String = ""
        fun bind(
            item: CurrencyModel
        ) {
            itemView.tv_currency_title.text = item.symbol
            itemView.et_currency.setText(item.rate.toString())
            if (!itemView.et_currency.isFocused) {
                itemView.et_currency.setText((item.rate!!.times(amount)).toString())
            }
            base = item.symbol
            setUpCurrencyChangedListener()
            itemView.setOnClickListener { swapItem() }
        }

        private fun swapItem() {
            Collections.swap(data, adapterPosition, 0)
            notifyItemMoved(adapterPosition, 0)
        }

        private fun setUpCurrencyChangedListener() {
            itemView.et_currency.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(input: Editable?) {
                    if (input.toString().isNotEmpty()) {
                        if (itemView.et_currency.isFocused) {
                            listener(base, input.toString().toFloat())
                        }
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(
                    input: CharSequence?,
                    start: Int,
                    befor: Int,
                    count: Int
                ) {
                }
            })
        }
    }

    fun updateAmount(amount: Float) {
        this.amount = amount
        notifyItemRangeChanged(0, data.size - 1, amount)
    }
}