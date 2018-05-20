package finom.list.ruf.listcryptkotlin.busines.interactor

import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency
import finom.list.ruf.listcryptkotlin.repository.RepositoryImpl
import io.reactivex.Single


class InteractorImpl private constructor() : Interactor {

    companion object {
        val INSTANCE = InteractorImpl()
    }

    private val repository = RepositoryImpl()

    override fun listCryptoCurrency(): Single<List<CryptoCurrency>> = repository.listCryptoCurrency()
            .toObservable()
            .flatMapIterable { it }
            .map { it.copyTo() }
            .toList()

}