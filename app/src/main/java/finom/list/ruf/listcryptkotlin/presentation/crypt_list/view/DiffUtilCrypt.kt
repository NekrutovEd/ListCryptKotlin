package finom.list.ruf.listcryptkotlin.presentation.crypt_list.view

import android.support.v7.util.DiffUtil
import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency

internal class DiffUtilCrypt(private val newCryptoCurrencies: List<CryptoCurrency>, private val oldCryptoCurrencies: List<CryptoCurrency>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldCryptoCurrencies.size

    override fun getNewListSize(): Int = newCryptoCurrencies.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = newCryptoCurrencies[newItemPosition].id == oldCryptoCurrencies[oldItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true
}