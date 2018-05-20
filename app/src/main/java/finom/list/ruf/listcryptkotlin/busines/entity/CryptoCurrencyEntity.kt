package finom.list.ruf.listcryptkotlin.busines.entity

import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency
import finom.list.ruf.listcryptkotlin.repository.dto.CryptoCurrencyDTO

data class CryptoCurrencyEntity(

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

    fun copyTo() = CryptoCurrency(
            id,
            name,
            symbol,
            rank,
            priceUsd,
            priceBtc,
            volumeUsd_24h,
            marketCapUsd,
            availableSupply,
            totalSupply,
            maxSupply,
            percentChange_1h,
            percentChange_24h,
            percentChange_7d,
            lastUpdated)

    companion object {
        fun castDTO(cryptoCurrencyDTO: CryptoCurrencyDTO) = CryptoCurrencyEntity(
                cryptoCurrencyDTO.id,
                cryptoCurrencyDTO.name,
                cryptoCurrencyDTO.symbol,
                cryptoCurrencyDTO.rank,
                cryptoCurrencyDTO.priceUsd,
                cryptoCurrencyDTO.priceBtc,
                cryptoCurrencyDTO.volumeUsd_24h,
                cryptoCurrencyDTO.marketCapUsd,
                cryptoCurrencyDTO.availableSupply,
                cryptoCurrencyDTO.totalSupply,
                cryptoCurrencyDTO.maxSupply,
                cryptoCurrencyDTO.percentChange_1h,
                cryptoCurrencyDTO.percentChange_24h,
                cryptoCurrencyDTO.percentChange_7d,
                cryptoCurrencyDTO.lastUpdated)
    }
}