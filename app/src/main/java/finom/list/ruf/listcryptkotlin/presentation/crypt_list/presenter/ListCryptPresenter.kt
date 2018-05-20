package finom.list.ruf.listcryptkotlin.presentation.crypt_list.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import finom.list.ruf.listcryptkotlin.busines.interactor.Interactor
import finom.list.ruf.listcryptkotlin.presentation.crypt_list.view.ListCryptView
import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

private const val TAG = "ListCryptPresenter"

@InjectViewState
class ListCryptPresenter : MvpPresenter<ListCryptView.View>(), LifecycleObserver {
    private val interactor = Interactor.INTERACTOR_IMPLEMENTATION
    private var cryptoCurrencies: List<CryptoCurrency> = ArrayList()
    private val compositeDisposableForPreparation = CompositeDisposable()
    private val compositeDisposableForLoading = CompositeDisposable()
    var queryOfSearch = ""
        set(value) {
            field = value
            preparationListCryptoCurrency(cryptoCurrencies)
        }

    var sortBy = SortBy.RANK
    var isAscendingSort = true


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (cryptoCurrencies.isEmpty()) loadListCryptoCurrency()
        else preparationListCryptoCurrency(cryptoCurrencies)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposableForLoading.clear()
        compositeDisposableForPreparation.clear()
    }

    private fun preparationListCryptoCurrency(newCryptoCurrencies: List<CryptoCurrency>) {
        compositeDisposableForPreparation.clear()
        compositeDisposableForPreparation.add(Observable.fromIterable(newCryptoCurrencies)
                .subscribeOn(Schedulers.computation())
                .filter { cryptoCurrency -> cryptoCurrency.contains(queryOfSearch) }
                .toList()
                .map { cryptoCurrencies ->
                    cryptoCurrencies.sortWith(sortBy.comparator)
                    if (!isAscendingSort) cryptoCurrencies.reverse()
                    cryptoCurrencies
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { cryptoCurrencies -> viewState.updateListCryptoCurrency(cryptoCurrencies) })
    }


    private fun loadListCryptoCurrency() {
        compositeDisposableForLoading.add(
                interactor.listCryptoCurrency()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally { viewState.hideLoading() }
                        .subscribe({ cryptoCurrencies ->
                            this.cryptoCurrencies = cryptoCurrencies
                            viewState.showListCryptoCurrency()
                            preparationListCryptoCurrency(cryptoCurrencies)
                        }, { this.handleError(it) }))
    }

    private fun handleError(throwable: Throwable) {
        //TODO обработка ошибок
        Log.e(TAG, "handleError: ${throwable.message}", throwable)
        viewState.showErrorMessage("Произошла ошибка")
    }

    fun onSwipeToRefresh() = loadListCryptoCurrency()


    fun onClickCryptoCurrency(cryptoCurrency: CryptoCurrency) = viewState.showCryptDetails(cryptoCurrency)


    fun onMenuItemSortClick(sortBy: SortBy) {
        when {
            this.sortBy === sortBy -> isAscendingSort = isAscendingSort.not()
            else -> {
                this.sortBy = sortBy
                isAscendingSort = true
            }
        }
        preparationListCryptoCurrency(cryptoCurrencies)
    }

}