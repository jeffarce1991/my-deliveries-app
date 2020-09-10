package com.jeff.template.main.list.view

import android.app.ProgressDialog
import android.app.ProgressDialog.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.template.BuildConfig
import com.jeff.template.R
import com.jeff.template.adapter.CustomAdapter
import com.jeff.template.android.base.extension.invokeSimpleDialog
import com.jeff.template.android.base.extension.longToast
import com.jeff.template.database.local.Photo
import com.jeff.template.databinding.ActivityMainBinding
import com.jeff.template.main.list.presenter.MainPresenter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_main.view.*
import javax.inject.Inject


class MainActivity : MvpActivity<MainView, MainPresenter>(),
    MainView {
    private lateinit var adapter: CustomAdapter
    private lateinit var progressDialog: ProgressDialog

    private lateinit var mainBinding : ActivityMainBinding

    lateinit var photos : List<Photo>


    @Inject
    internal lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainPresenter.getPhotos()

        setUpToolbarTitle()
        mainBinding.root.swipeRefreshLayout.setOnRefreshListener {
            mainPresenter.getPhotos()
        }
    }

    private fun setUpToolbarTitle() {
        setSupportActionBar(mainBinding.toolbar)

        //supportActionBar!!.title = getString(R.string.app_name)
        supportActionBar!!.title = resources.getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.about -> {
                invokeSimpleDialog(
                    getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME,
                    getString(R.string.about_description)
                            + "\nDeveloped by : Jeff Arce"
                )
            }
        }

        return super.onOptionsItemSelected(item)
    }


    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateDataList(photos: List<Photo>) {
        adapter = CustomAdapter(this, photos)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.root.customRecyclerView.layoutManager = layoutManager
        mainBinding.root.customRecyclerView.adapter = adapter
    }

    override fun createPresenter(): MainPresenter {
        return mainPresenter
    }

    override fun hideProgress() {
        mainBinding.root.swipeRefreshLayout.isRefreshing = false
    }
    override fun showProgress() {
        mainBinding.root.swipeRefreshLayout.isRefreshing = true
    }

    override fun showLoadingDataFailed() {
        longToast("Loading data failed")
        /*invokeSimpleDialog("Project420",
            "OK",
            "List is empty or null.")*/
    }

    override fun showToast(message: String) {
        longToast(message)
    }

    override fun showProgressRemote() {
        progressDialog = show(
            this,
            resources.getString(R.string.app_name),
            "Loading data remotely...")
    }

    override fun showProgressLocal() {
        progressDialog = show(
            this,
            resources.getString(R.string.app_name),
            "Loading data locally...")
    }
}
