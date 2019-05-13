package com.vp.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.text.BoringLayout
import android.view.Menu
import android.view.MenuItem
import androidx.room.Room

import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.viewmodel.DetailsViewModel
import com.vp.favorites.AppDatabase
import com.vp.favorites.Favorite
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.run

class DetailActivity : DaggerAppCompatActivity(), QueryProvider {

    private var mDB: AppDatabase? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val detailViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        binding.viewModel = detailViewModel
        queryProvider = this
        binding.setLifecycleOwner(this)
        detailViewModel.fetchDetails()
        detailViewModel.title().observe(this, Observer {
            supportActionBar?.title = it
        })
        detailViewModel.details().observe(this, Observer {
            favoriteObject = Favorite(getMovieId(), it.title, it.year, it.poster)
        })
        initmDB()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        setMenuValue(menu?.getItem(0)!!)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val idMenu = item.itemId

        if (idMenu == R.id.star) {
            manageBD(item.isChecked, item)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun getMovieId(): String {
        return intent?.data?.getQueryParameter("imdbID") ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    fun initmDB() {
        mDB = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().build()
    }
    fun manageBD(isChecked: Boolean, itemMenu: MenuItem) {
        if (!isChecked) {
            mDB?.favoriteDao()?.insert(favoriteObject)
            itemMenu.setIcon(R.drawable.ic_star_white)
            itemMenu.isChecked = true
        } else if (isChecked){
            mDB?.favoriteDao()?.delete(favoriteObject)
            itemMenu.setIcon(R.drawable.ic_star)
            itemMenu.isChecked = false
        }
    }

    fun setMenuValue(itemMenu: MenuItem) {
        if (isAddedToDB()) {
            itemMenu?.setIcon(R.drawable.ic_star_white)
            itemMenu?.isChecked = true
        }

    }

    fun isAddedToDB(): Boolean {
        if (mDB?.favoriteDao()?.
                        findFavoriteById(getMovieId()) != null) {
            return true
        }
        return false
    }

    companion object {
        lateinit var queryProvider: QueryProvider
        const val DATABASE_NAME = "IMDB_APP"
        lateinit var favoriteObject: Favorite
    }
}
