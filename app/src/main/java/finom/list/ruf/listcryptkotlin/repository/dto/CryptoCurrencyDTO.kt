package finom.list.ruf.listcryptkotlin.repository.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoCurrencyDTO(

        @SerializedName("id")
        @Expose val id: String,

        @SerializedName("name")
        @Expose var name: String,

        @SerializedName("symbol")
        @Expose var symbol: String,

        @SerializedName("rank")
        @Expose var rank: Int,

        @SerializedName("price_usd")
        @Expose var priceUsd: Float,

        @SerializedName("price_btc")
        @Expose var priceBtc: Float,

        @SerializedName("24h_volume_usd")
        @Expose var volumeUsd_24h: Double,

        @SerializedName("market_cap_usd")
        @Expose var marketCapUsd: Long,

        @SerializedName("available_supply")
        @Expose var availableSupply: Long,

        @SerializedName("total_supply")
        @Expose var totalSupply: Long,

        @SerializedName("max_supply")
        @Expose var maxSupply: Long?,

        @SerializedName("percent_change_1h")
        @Expose var percentChange_1h: Float,

        @SerializedName("percent_change_24h")
        @Expose var percentChange_24h: Float,

        @SerializedName("percent_change_7d")
        @Expose var percentChange_7d: Float,

        @SerializedName("last_updated")
        @Expose var lastUpdated: Long

)