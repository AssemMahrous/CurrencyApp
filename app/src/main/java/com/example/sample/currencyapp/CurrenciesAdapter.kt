package com.example.sample.currencyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currencies_signle_item.view.*

class CurrenciesAdapter(
    var data: List<CurrencyModel>,
    val listener: (id: Int, position: Int) -> Unit
) : RecyclerView.Adapter<CurrenciesAdapter.CurrenciesHolder>() {

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
        holder.bind(data[position], listener)
    }

    class CurrenciesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CurrencyModel, listener: (id: Int, position: Int) -> Unit) =
            with(itemView) {

                tv_currency_title.text = item.pref
                et_currency.setText(item.name)

                itemView.setOnClickListener { }

            }
    }
}