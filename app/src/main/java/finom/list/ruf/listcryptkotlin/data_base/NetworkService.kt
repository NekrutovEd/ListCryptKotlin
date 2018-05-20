package finom.list.ruf.listcryptkotlin.data_base

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val URL = "https://api.coinmarketcap.com/v1/"

class NetworkService {
    companion object {
        val cryptoApi: CryptoApi = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build().create(CryptoApi::class.java)
    }
}