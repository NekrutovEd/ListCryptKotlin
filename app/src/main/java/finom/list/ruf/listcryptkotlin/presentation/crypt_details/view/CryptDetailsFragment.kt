package finom.list.ruf.listcryptkotlin.presentation.crypt_details.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import finom.list.ruf.listcryptkotlin.R
import finom.list.ruf.listcryptkotlin.presentation.crypt_details.presenter.CryptDetailsPresenter
import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency
import kotlinx.android.synthetic.main.fragment_crypt_details.view.*
import java.text.NumberFormat
import java.util.*

class CryptDetailsFragment : MvpAppCompatFragment(), CryptDetailsView.View {

    @InjectPresenter
    lateinit var presenter: CryptDetailsPresenter

    @ProvidePresenter
    fun providePresenter() = CryptDetailsPresenter(cryptoCurrency)


    companion object {
        fun newInstance(cryptoCurrency: CryptoCurrency): CryptDetailsFragment {
            val args = Bundle()
            val fragment = CryptDetailsFragment()
            fragment.arguments = args
            fragment.cryptoCurrency = cryptoCurrency
            return fragment
        }
    }

    private lateinit var cryptoCurrency: CryptoCurrency

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_crypt_details, container, false)
        initToolbar(root.fragment_crypt_details_toolbar)

        val cryptoCurrency = presenter.cryptoCurrency
        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        val format = NumberFormat.getInstance(Locale.US)

        root.fragment_crypt_details_symbol.text = cryptoCurrency.symbol
        root.fragment_crypt_details_name.text = cryptoCurrency.name
        root.fragment_crypt_details_price_usd.text = formatCurrency.format(cryptoCurrency.priceUsd.toDouble())
        root.fragment_crypt_details_market_cap_usd.text = formatCurrency.format(cryptoCurrency.marketCapUsd)
        root.fragment_crypt_details_volume_usd_24h.text = formatCurrency.format(cryptoCurrency.volumeUsd_24h)
        root.fragment_crypt_details_available_supply.text = formatCurrency.format(cryptoCurrency.availableSupply)
        root.fragment_crypt_details_price_btc.text = String.format("%s BTC", format.format(cryptoCurrency.priceBtc.toDouble()))
        root.fragment_crypt_details_total_supply.text = String.format("%s %s", format.format(cryptoCurrency.totalSupply), cryptoCurrency.symbol)
        initTextPercent(root.fragment_crypt_details_percent_change_1h, cryptoCurrency.percentChange_1h)
        initTextPercent(root.fragment_crypt_details_percent_change_24h, cryptoCurrency.percentChange_24h)
        initTextPercent(root.fragment_crypt_details_percent_change_7d, cryptoCurrency.percentChange_7d)

        if (cryptoCurrency.maxSupply == null) {
            root.group_max_supply.visibility = View.GONE
        } else {
            root.group_max_supply.visibility = View.VISIBLE
            root.fragment_crypt_details_max_supply.text = String.format("%s %s", format.format(cryptoCurrency.maxSupply!!), cryptoCurrency.symbol)
        }

        return root
    }

    private fun initToolbar(toolbar: Toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_white_24px)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        setHasOptionsMenu(true)
    }

    private fun initTextPercent(textViewPercent: TextView, value: Float) {
        textViewPercent.text = String.format(when {
            value > 0 -> "+%s%%"
            else -> "%s%%"
        }, value)

        textViewPercent.setTextColor(
                textViewPercent.resources.getColor(when {
                    value < 0 -> R.color.colorRed
                    else -> R.color.colorGreen
                }))
    }

    private fun onBackPressed() {
        activity!!.onBackPressed()
    }
}