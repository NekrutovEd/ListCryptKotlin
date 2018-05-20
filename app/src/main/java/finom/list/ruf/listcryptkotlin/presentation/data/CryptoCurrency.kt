package finom.list.ruf.listcryptkotlin.presentation.data

data class CryptoCurrency(
        val id: String,
        var name: String,
        var symbol: String,
        var rank: Int,
        var priceUsd: Float,
        var priceBtc: Float,
        var volumeUsd_24h: Double,
        var marketCapUsd: Long,
        var availableSupply: Long,
        var totalSupply: Long,
        var maxSupply: Long?,
        var percentChange_1h: Float,
        var percentChange_24h: Float,
        var percentChange_7d: Float,
        var lastUpdated: Long
) {

    /**
     * Returns true if and only if {@code CryptoCurrency.name} or {@code CryptoCurrency.symbol} contains string.
     * Without the register
     *
     * @param textSearch the string to search for
     */
    fun contains(textSearch: String) =
            name.toLowerCase().contains(textSearch.toLowerCase())
                    || symbol.toLowerCase().contains(textSearch.toLowerCase())


    fun compareRank(cryptoCurrency: CryptoCurrency) = when {
        this.rank > cryptoCurrency.rank -> 1
        this.rank < cryptoCurrency.rank -> -1
        else -> 0
    }

    fun comparePriceUsd(cryptoCurrency: CryptoCurrency) = when {
        this.priceUsd > cryptoCurrency.priceUsd -> 1
        this.priceUsd < cryptoCurrency.priceUsd -> -1
        else -> 0
    }

    fun compareMarketCapUsd(cryptoCurrency: CryptoCurrency) = when {
        this.marketCapUsd > cryptoCurrency.marketCapUsd -> 1
        this.marketCapUsd < cryptoCurrency.marketCapUsd -> -1
        else -> 0
    }

    fun compareVolumeUsd(cryptoCurrency: CryptoCurrency) = when {
        this.volumeUsd_24h > cryptoCurrency.volumeUsd_24h -> 1
        this.volumeUsd_24h < cryptoCurrency.volumeUsd_24h -> -1
        else -> 0
    }

    fun comparePercentChange24h(cryptoCurrency: CryptoCurrency) = when {
        this.percentChange_24h > cryptoCurrency.percentChange_24h -> 1
        this.percentChange_24h < cryptoCurrency.percentChange_24h -> -1
        else -> 0
    }
}