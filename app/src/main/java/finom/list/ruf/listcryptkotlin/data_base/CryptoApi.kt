package finom.list.ruf.listcryptkotlin.data_base

import finom.list.ruf.listcryptkotlin.repository.dto.CryptoCurrencyDTO
import io.reactivex.Single
import retrofit2.http.GET

interface CryptoApi {
    @GET("ticker")
    fun ticker(): Single<List<CryptoCurrencyDTO>>
}