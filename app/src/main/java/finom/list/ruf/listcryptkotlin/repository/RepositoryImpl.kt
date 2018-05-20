package finom.list.ruf.listcryptkotlin.repository

import finom.list.ruf.listcryptkotlin.busines.entity.CryptoCurrencyEntity
import finom.list.ruf.listcryptkotlin.data_base.NetworkService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RepositoryImpl : Repository {

    override fun listCryptoCurrency(): Single<List<CryptoCurrencyEntity>> =
            NetworkService.cryptoApi.ticker()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .flatMapIterable { it }
                    .map { CryptoCurrencyEntity.castDTO(it) }
                    .toList()
}