package finom.list.ruf.listcryptkotlin.repository

import finom.list.ruf.listcryptkotlin.busines.entity.CryptoCurrencyEntity
import io.reactivex.Single

interface Repository {
    fun listCryptoCurrency(): Single<List<CryptoCurrencyEntity>>
}