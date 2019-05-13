package com.vp.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.SearchView

import javax.inject.Inject

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

class MovieListActivity : AppCompatActivity(), HasSupportFragmentInjector {


    var dispatchingActivityInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject set
    private var searchView: SearchView? = null
    private var searchViewExpanded = true
    private var queryPending: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, ListFragment(), ListFragment.TAG)
                    .commit()
        } else {
            searchViewExpanded = savedInstanceState.getBoolean(IS_SEARCH_VIEW_ICONIFIED)
            queryPending = savedInstanceState.getString(IS_ANY_QUERY_PENDING)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        val menuItem = menu.findItem(R.id.search)

        searchView = menuItem.actionView as SearchView
        searchView!!.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
        searchView!!.isIconified = searchViewExpanded
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val listFragment = supportFragmentManager.findFragmentByTag(ListFragment.TAG) as ListFragment?
                listFragment!!.submitSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                queryPending = newText
                return false
            }
        })

        isAnyQueryPending()

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_SEARCH_VIEW_ICONIFIED, searchView!!.isIconified)
        outState.putString(IS_ANY_QUERY_PENDING, queryPending)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return dispatchingActivityInjector
    }

    private fun isAnyQueryPending() {
        if (queryPending != null && !queryPending!!.isEmpty()) {
            searchView!!.setQuery(queryPending, false)
        }
    }

    companion object {
        private val IS_SEARCH_VIEW_ICONIFIED = "is_search_view_iconified"
        private val IS_ANY_QUERY_PENDING = "is_any_query_pending"
    }
}
