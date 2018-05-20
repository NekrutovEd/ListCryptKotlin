package finom.list.ruf.listcryptkotlin.presentation.crypt_list.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency

interface ListCryptView {

    interface View : MvpView {

        fun updateListCryptoCurrency(cryptoCurrencies: List<CryptoCurrency>)

        fun hideLoading()

        fun showListCryptoCurrency()

        @StateStrategyType(SkipStrategy::class)
        fun showCryptDetails(cryptoCurrency: CryptoCurrency)

        @StateStrategyType(SkipStrategy::class)
        fun showErrorMessage(errorMessage: String)
    }
}