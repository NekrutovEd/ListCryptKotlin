package finom.list.ruf.listcryptkotlin.busines.interactor

import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency
import io.reactivex.Single

interface Interactor {

    companion object {
        val INTERACTOR_IMPLEMENTATION: Interactor = InteractorImpl.INSTANCE
    }

    fun listCryptoCurrency(): Single<List<CryptoCurrency>>
}