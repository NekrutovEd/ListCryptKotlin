package finom.list.ruf.listcryptkotlin.presentation.crypt_list.presenter

import android.support.annotation.StringRes
import finom.list.ruf.listcryptkotlin.R
import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency

enum class SortBy(@StringRes val title: Int, val comparator: java.util.Comparator<CryptoCurrency>) {

    RANK(R.string.sort_by_rank, Comparator<CryptoCurrency> { obj, cryptoCurrency -> obj.compareRank(cryptoCurrency) }),
    PRICE(R.string.sort_by_price, Comparator<CryptoCurrency> { obj, cryptoCurrency -> obj.comparePriceUsd(cryptoCurrency) }),
    VOLUME(R.string.sort_by_volume, Comparator<CryptoCurrency> { obj, cryptoCurrency -> obj.compareVolumeUsd(cryptoCurrency) }),
    MARKET_CAP(R.string.sort_by_market_cap, Comparator<CryptoCurrency> { obj, cryptoCurrency -> obj.compareMarketCapUsd(cryptoCurrency) }),
    PERCENT_CHANGE(R.string.sort_by_percent_change, Comparator<CryptoCurrency> { obj, cryptoCurrency -> obj.comparePercentChange24h(cryptoCurrency) })
}