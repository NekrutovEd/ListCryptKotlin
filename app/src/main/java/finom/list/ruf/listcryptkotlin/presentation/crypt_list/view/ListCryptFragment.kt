package finom.list.ruf.listcryptkotlin.presentation.crypt_list.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.content.res.TypedArrayUtils.getText
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.widget.*
import android.view.*
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import finom.list.ruf.listcryptkotlin.R
import finom.list.ruf.listcryptkotlin.R.id.*
import finom.list.ruf.listcryptkotlin.presentation.crypt_list.presenter.ListCryptPresenter
import finom.list.ruf.listcryptkotlin.presentation.crypt_list.presenter.SortBy
import finom.list.ruf.listcryptkotlin.presentation.crypt_list.view.ListCryptRecyclerAdapter.IOnClickCryptoCurrencyListener
import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency
import finom.list.ruf.listcryptkotlin.presentation.main.ListCryptActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_crypt_details.view.*
import kotlinx.android.synthetic.main.fragment_list_crypt.*
import kotlinx.android.synthetic.main.fragment_list_crypt.view.*
import java.util.concurrent.TimeUnit

class ListCryptFragment : MvpAppCompatFragment(), ListCryptView.View {

    @InjectPresenter
    lateinit var presenter: ListCryptPresenter

    @ProvidePresenter
    fun providePresenter() = ListCryptPresenter()

    private lateinit var adapter: ListCryptRecyclerAdapter

    private val layoutManager: RecyclerView.LayoutManager
        get() = when {
            Configuration.ORIENTATION_LANDSCAPE == resources.configuration.orientation -> GridLayoutManager(context, 2)
            else -> LinearLayoutManager(context)
        }

    companion object {
        fun newInstance(): ListCryptFragment {
            val args = Bundle()
            val fragment = ListCryptFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity !is ListCryptActivity) {
            throw ClassCastException("${activity!!.localClassName} should extends ListCryptActivity")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_list_crypt, container, false)
        lifecycle.addObserver(presenter)
        initToolbar(root)

        root.fragment_list_crypt_swipe_refresh.setOnRefreshListener { presenter.onSwipeToRefresh() }

        adapter = ListCryptRecyclerAdapter(
                object : IOnClickCryptoCurrencyListener {
                    override fun onClickCryptoCurrency(cryptoCurrency: CryptoCurrency) =
                            presenter.onClickCryptoCurrency(cryptoCurrency)
                })

        root.fragment_list_crypt_recycler_view.layoutManager = layoutManager
        root.fragment_list_crypt_recycler_view.adapter = adapter

        return root
    }

    private fun initToolbar(root: View) {
        (activity as AppCompatActivity).setSupportActionBar(root.fragment_crypt_details_toolbar)
        initSearchView(root.fragment_list_crypt_toolbar_search)
        root.fragment_list_crypt_toolbar_sort.setOnClickListener { initSortMenu(it) }
        setHasOptionsMenu(true)
    }

    @SuppressLint("RestrictedApi")
    private fun initSortMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view, Gravity.BOTTOM)

        val menu = popupMenu.menu
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }

        for (sortByItem in SortBy.values()) {
            val menuItem = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, sortByItem.title)
            if (sortByItem === presenter.sortBy) {
                menuItem.setIcon(
                        if (presenter.isAscendingSort) R.drawable.ic_baseline_expand_more_black_24px
                        else R.drawable.ic_baseline_expand_less_black_24px)
            }
            menuItem.setOnMenuItemClickListener {
                presenter.onMenuItemSortClick(sortByItem)
                popupMenu.dismiss()
                true
            }
        }
        popupMenu.show()
    }

    @SuppressLint("CheckResult")
    private fun initSearchView(searchView: SearchView) {
        val searchAutoComplete = searchView
                .findViewById<SearchView.SearchAutoComplete>(android.support.v7.appcompat.R.id.search_src_text)

        val colorWhite = resources.getColor(R.color.colorWhite)
        searchAutoComplete.setTextColor(colorWhite)
        searchAutoComplete.setHintTextColor(colorWhite)
        searchAutoComplete.setLinkTextColor(colorWhite)
        searchAutoComplete.highlightColor = colorWhite

        searchView.setIconifiedByDefault(false)
        searchView.queryHint = getText(R.string.search)
        searchView.setQuery(presenter.queryOfSearch, false)

        RxSearchView.queryTextChanges(searchView)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(1, TimeUnit.SECONDS)
                .map<String> { it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { presenter.queryOfSearch = it }
    }

    override fun updateListCryptoCurrency(cryptoCurrencies: List<CryptoCurrency>) {
        val instanceState = fragment_list_crypt_recycler_view.layoutManager.onSaveInstanceState()
        adapter.cryptoCurrencies = cryptoCurrencies
        fragment_list_crypt_recycler_view.layoutManager.onRestoreInstanceState(instanceState)
    }

    override fun hideLoading() {
        fragment_list_crypt_progress_bar.visibility = View.GONE
        fragment_list_crypt_swipe_refresh.isRefreshing = false
    }

    override fun showListCryptoCurrency() {
        fragment_list_crypt_swipe_refresh.visibility = View.VISIBLE
    }

    override fun showCryptDetails(cryptoCurrency: CryptoCurrency) {
        (activity as ListCryptActivity).showCryptDetailsFragment(cryptoCurrency)
    }

    override fun showErrorMessage(errorMessage: String) = Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
}
