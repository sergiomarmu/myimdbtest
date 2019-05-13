package com.vp.list;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MovieListActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private static final String IS_SEARCH_VIEW_ICONIFIED = "is_search_view_iconified";
    private static final String IS_ANY_QUERY_PENDING = "is_any_query_pending";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingActivityInjector;
    private SearchView searchView;
    private boolean searchViewExpanded = true;
    private String queryPending = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new ListFragment(), ListFragment.TAG)
                    .commit();
        } else {
            searchViewExpanded = savedInstanceState.getBoolean(IS_SEARCH_VIEW_ICONIFIED);
            //3
            queryPending = savedInstanceState.getString(IS_ANY_QUERY_PENDING);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);

        searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        searchView.setIconified(searchViewExpanded);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag(ListFragment.TAG);
                listFragment.submitSearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //3
                queryPending = newText;
                return false;
            }
        });

        isAnyQueryPending();

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_SEARCH_VIEW_ICONIFIED, searchView.isIconified());
        //3
        outState.putString(IS_ANY_QUERY_PENDING, queryPending);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingActivityInjector;
    }

    private void isAnyQueryPending() {
        if (queryPending != null && !queryPending.isEmpty()) {
            searchView.setQuery(queryPending, false);
        }
    }
}
