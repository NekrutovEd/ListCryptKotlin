package finom.list.ruf.listcryptkotlin.presentation.crypt_list.view

import android.support.annotation.ColorRes
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import finom.list.ruf.listcryptkotlin.R
import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

internal class ListCryptRecyclerAdapter(private val onClickCryptoCurrencyListener: IOnClickCryptoCurrencyListener) : RecyclerView.Adapter<ListCryptRecyclerAdapter.ListCryptViewHolder>() {

    internal var cryptoCurrencies: List<CryptoCurrency> = ArrayList()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(DiffUtilCrypt(value, field))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCryptViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListCryptViewHolder(inflater.inflate(R.layout.view_holder_list_crypt_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListCryptViewHolder, position: Int) =
            holder.setCryptoCurrency(cryptoCurrencies[position])

    override fun getItemCount() = cryptoCurrencies.size

    internal inner class ListCryptViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val constraintLayout = itemView.findViewById<ConstraintLayout>(R.id.view_holder_list_crypt_item_constraint_layout)
        private val symbolTextView = itemView.findViewById<TextView>(R.id.view_holder_list_crypt_item_symbol)
        private val nameTextView = itemView.findViewById<TextView>(R.id.view_holder_list_crypt_item_name)
        private val marketCapTextView = itemView.findViewById<TextView>(R.id.view_holder_list_crypt_item_market_cap)
        private val volumeTextView = itemView.findViewById<TextView>(R.id.view_holder_list_crypt_item_volume)
        private val priceTextView = itemView.findViewById<TextView>(R.id.view_holder_list_crypt_item_price)
        private val priceChangeTextView = itemView.findViewById<TextView>(R.id.view_holder_list_crypt_item_percent_change_24h)

        internal fun setCryptoCurrency(cryptoCurrency: CryptoCurrency) {
            symbolTextView.text = cryptoCurrency.symbol
            nameTextView.text = cryptoCurrency.name

            val format = NumberFormat.getCurrencyInstance(Locale.US)
            marketCapTextView.text = String.format("MC: %s", format.format(cryptoCurrency.marketCapUsd))
            volumeTextView.text = String.format("V: %s", format.format(cryptoCurrency.volumeUsd_24h))
            priceTextView.text = format.format(cryptoCurrency.priceUsd)

            priceChangeTextView.setTextColor(
                    priceChangeTextView.resources.getColor(when {
                        cryptoCurrency.percentChange_24h < 0 -> R.color.colorRed
                        else -> R.color.colorGreen
                    }))


            val formatPercent = when {
                cryptoCurrency.percentChange_24h > 0 -> "+%s%%"
                else -> "%s%%"
            }
            priceChangeTextView.text = String.format(formatPercent, cryptoCurrency.percentChange_24h)

            constraintLayout.setOnClickListener { onClickCryptoCurrencyListener.onClickCryptoCurrency(cryptoCurrency) }
        }
    }

    internal interface IOnClickCryptoCurrencyListener {
        fun onClickCryptoCurrency(cryptoCurrency: CryptoCurrency)
    }
}