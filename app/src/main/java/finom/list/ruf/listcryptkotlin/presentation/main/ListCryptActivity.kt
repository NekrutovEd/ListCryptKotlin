package finom.list.ruf.listcryptkotlin.presentation.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import finom.list.ruf.listcryptkotlin.R
import finom.list.ruf.listcryptkotlin.presentation.crypt_details.view.CryptDetailsFragment
import finom.list.ruf.listcryptkotlin.presentation.crypt_list.view.ListCryptFragment
import finom.list.ruf.listcryptkotlin.presentation.data.CryptoCurrency

class ListCryptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_crypt)
    }

    override fun onStart() {
        super.onStart()
        initListCryptFragment()
    }

    private fun initListCryptFragment() {
        var fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.activity_main_fragment_layout)
        if (fragment == null) {
            fragment = ListCryptFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.activity_main_fragment_layout, fragment)
                    .commit()
        }
    }

    fun showCryptDetailsFragment(cryptoCurrency: CryptoCurrency) =
            supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.activity_main_fragment_layout, CryptDetailsFragment.newInstance(cryptoCurrency))
                    .addToBackStack(null)
                    .commit()
}