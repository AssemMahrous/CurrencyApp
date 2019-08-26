package com.example.sample.currencyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currencies_signle_item.view.*
import java.util.*

class CurrenciesAdapter(
    val listener: (id: String, position: Int) -> Unit
) : RecyclerView.Adapter<CurrenciesAdapter.CurrenciesHolder>() {
    var data = ArrayList<CurrencyModel>()
    private var amount = 1.0F


    fun addRates(currencies: ArrayList<CurrencyModel>) {
        if (data.isEmpty()) {
            data.addAll(currencies)
        }

        for (currency in currencies) {
            data.forEachIndexed { index, element ->
                if (element.symbol == currency.symbol)
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
        holder.bind(data[position], listener, position)
        swapItem(position)
    }

    class CurrenciesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: CurrencyModel,
            listener: (id: String, position: Int) -> Unit,
            position: Int
        ) {
            itemView.tv_currency_title.text = item.symbol
            itemView.et_currency.setText(item.rate.toString())
            itemView.setOnClickListener { listener(item.symbol, position) }

        }

    }

    fun swapItem(position: Int) {
        Collections.swap(data, position, 0);
        notifyItemMoved(position, 0);
    }


}