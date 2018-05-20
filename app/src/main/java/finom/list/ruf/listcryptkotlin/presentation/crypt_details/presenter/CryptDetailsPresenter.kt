package finom.list.ruf.listcryptkotlin.presentation.crypt_details.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import finom.list.ruf.listcryptkotlin.presentation.crypt_details.view.CryptDetailsView
import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency

@InjectViewState
class CryptDetailsPresenter(val cryptoCurrency: CryptoCurrency) : MvpPresenter<CryptDetailsView.View>()