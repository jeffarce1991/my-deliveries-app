package com.jeff.deliveries.main.list.view

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.deliveries.BuildConfig
import com.jeff.deliveries.R
import com.jeff.deliveries.adapter.DeliveriesAdapter
import com.jeff.deliveries.android.base.extension.invokeSimpleDialog
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.local.Favorite
import com.jeff.deliveries.databinding.ActivityMainBinding
import com.jeff.deliveries.main.list.presenter.MainPresenter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_main.view.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : MvpActivity<MainView, MainPresenter>(),
    MainView {
    private lateinit var adapter: DeliveriesAdapter
    private lateinit var progressDialog: ProgressDialog

    private lateinit var binding : ActivityMainBinding

    lateinit var deliveries : List<Delivery>


    @Inject
    internal lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        mainPresenter.loadInitialLocally()
        mainPresenter.loadInitialDeliveries()

        setUpToolbarTitle()
        binding.root.swipeRefreshLayout.setOnRefreshListener {
            mainPresenter.loadInitialDeliveries()
        }
        initScrollListener()
        mainPresenter.observeFavorites(this)
    }

    private fun setUpToolbarTitle() {
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.title = getString(R.string.my_deliveries)
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

    private fun initScrollListener() {
        binding.root.customRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                @NonNull recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(
                @NonNull recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                //if (!isLoading) {
                if (layoutManager != null
                    && layoutManager.findLastCompletelyVisibleItemPosition()
                    == adapter.itemCount - 1
                    && adapter.itemCount > 19) {
                    Timber.d("==q Loading.. more users")
                    mainPresenter.loadMoreDeliveries(adapter.getLastIndex())
                    //isLoading = true
                }
                //}
            }
        })
    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateDeliveryList(deliveries: List<Delivery>) {
        adapter = DeliveriesAdapter(this, deliveries as MutableList<Delivery>, emptyList())
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        binding.root.customRecyclerView.layoutManager = layoutManager
        binding.root.customRecyclerView.adapter = adapter
    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateDeliveryList(deliveries: List<Delivery>, favoriteList: List<Favorite>) {
        adapter = DeliveriesAdapter(this, deliveries as MutableList<Delivery>, favoriteList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        binding.root.customRecyclerView.layoutManager = layoutManager
        binding.root.customRecyclerView.adapter = adapter
    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateMoreDeliveries(deliveries: List<Delivery>) {
        adapter.addAll(deliveries as MutableList<Delivery>)
        //mainBinding.root.customRecyclerView.smoothScrollToPosition(adapter.itemCount-2)
    }

    override fun createPresenter(): MainPresenter {
        return mainPresenter
    }

    override fun hideProgress() {
        binding.root.swipeRefreshLayout.isRefreshing = false
    }
    override fun showProgress() {
        binding.root.swipeRefreshLayout.isRefreshing = true
    }

    override fun showMessage(message: String) {
        Snackbar.make(binding.coordLayout,
            message,
            Snackbar.LENGTH_SHORT)
            .show()
    }


}
