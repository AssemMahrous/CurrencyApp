package com.example.sample.currencyapp

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currencies_single_item.view.*
import java.util.*

class CurrenciesAdapter(
    val listener: (id: String, value: Float) -> Unit
) : RecyclerView.Adapter<CurrenciesAdapter.CurrenciesHolder>() {
    private var data = HashMap<String?, CurrencyModel>()
    private var bases = ArrayList<String>()
    private var amount = 1.0F

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesHolder {
        return CurrenciesHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.currencies_single_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CurrenciesHolder, position: Int) {
        holder.bind(getRate(position))
    }

    inner class CurrenciesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var base: String = ""
        fun bind(item: CurrencyModel) {
            if (base != item.base) {
                this.base = item.base
                itemView.tv_currency_title.text = item.base
                itemView.tv_currency_base.text = item.base
                setUpCurrencyChangedListener()
                itemView.et_currency.onFocusChangeListener =
                    View.OnFocusChangeListener { _, hasFocus ->
                        if (!hasFocus) {
                            return@OnFocusChangeListener
                        }
                        swapItem()
                    }
            }
            if (!itemView.et_currency.isFocused) {
                val value = item.rate!!.times(amount).toString()
                itemView.et_currency.setText(value)
            }
        }

        private fun swapItem() {
            adapterPosition.takeIf { it > 0 }?.also { currentPosition ->
                bases.removeAt(currentPosition).also {
                    bases.add(0, it)
                }
                notifyItemMoved(currentPosition, 0)
            }
        }

        private fun setUpCurrencyChangedListener() {
            itemView.et_currency.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(input: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(
                    input: CharSequence?,
                    start: Int,
                    befor: Int,
                    count: Int
                ) {
                    if (input.toString().isNotEmpty()) {
                        if (itemView.et_currency.isFocused) {
                            listener(base, input.toString().toFloat())
                        }
                    }
                }
            })
        }
    }

    fun addRates(currencies: ArrayList<CurrencyModel>) {
        if (bases.isEmpty()) {
            bases.addAll(currencies.map { it.base })
        }
        for (currency in currencies) {
            data[currency.base] = currency
        }
        notifyItemRangeChanged(0, bases.size - 1, amount)
    }

    fun updateAmount(amount: Float) {
        this.amount = amount
        notifyItemRangeChanged(0, bases.size - 1, this.amount)
    }

    private fun getRate(pos: Int): CurrencyModel {
        return data[bases[pos]]!!
    }

    override fun getItemCount(): Int {
        return bases.size
    }
}