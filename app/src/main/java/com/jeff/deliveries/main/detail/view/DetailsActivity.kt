package com.jeff.deliveries.main.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jakewharton.picasso.OkHttp3Downloader
import com.jeff.deliveries.R
import com.jeff.deliveries.android.base.extension.hide
import com.jeff.deliveries.databinding.ActivityDetailsBinding
import com.jeff.deliveries.main.detail.presenter.DetailsPresenter
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_details.view.*
import kotlinx.android.synthetic.main.content_details.view.from
import kotlinx.android.synthetic.main.content_details.view.from_shimmer
import kotlinx.android.synthetic.main.content_details.view.to
import kotlinx.android.synthetic.main.content_details.view.to_shimmer
import kotlinx.android.synthetic.main.item_delivery.view.*
import javax.inject.Inject


class DetailsActivity : MvpActivity<DetailsView, DetailsPresenter>(),
    DetailsView {

    companion object {
        private var EXTRA_ID = "EXTRA_ID"
        private var EXTRA_START = "EXTRA_START"
        private var EXTRA_END = "EXTRA_END"
        private var EXTRA_GOODS_PICTURE = "EXTRA_GOODS_PICTURE"
        private var EXTRA_PRICE = "EXTRA_PRICE"

        fun getStartIntent(
            context: Context,
            id : String,
            start : String,
            end : String,
            goodsPicture : String,
            price: String
        ): Intent {
            return Intent(context, DetailsActivity::class.java)
                .putExtra(EXTRA_ID, id)
                .putExtra(EXTRA_START, start)
                .putExtra(EXTRA_END, end)
                .putExtra(EXTRA_GOODS_PICTURE, goodsPicture)
                .putExtra(EXTRA_PRICE, price)
        }
    }

    @Inject
    internal lateinit var detailsPresenter : DetailsPresenter

    private lateinit var binding : ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        setupToolbar()
        startShimmerAnimations()
        Thread.sleep(2)
        stopShimmerAnimations()
        hideShimmerPlaceholders()

        setDetails(getStart()!!, getEnd()!!, getGoodsPicture()!!, getPrice()!!)
        //userDetailsPresenter.loadUserDetails(getUserName()!!, getId()!!)
        //userDetailsPresenter.loadNotes(getId()!!)
        /*binding.root.save_notes.setOnClickListener {
            userDetailsPresenter.updateNotes(
                binding.root.notes.text.toString(),
                getId()!!
            )
        }*/
    }

    private fun getId(): Int? = intent.getIntExtra(EXTRA_ID, -1)
    private fun getStart(): String? = intent.getStringExtra(EXTRA_START)
    private fun getEnd(): String? = intent.getStringExtra(EXTRA_END)
    private fun getGoodsPicture(): String? = intent.getStringExtra(EXTRA_GOODS_PICTURE)
    private fun getPrice(): String? = intent.getStringExtra(EXTRA_PRICE)

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.title = resources.getString(R.string.delivery_details)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun createPresenter(): DetailsPresenter {
        return detailsPresenter
    }

    private fun setDetails(from: String, to: String, goodsPicture: String, price: String) {
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this))
        builder.build().load(goodsPicture)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(binding.root.goods_to_deliver)

        binding.root.from.text = from
        binding.root.to.text = to
        binding.root.delivery_fee.text = price
    }

    override fun showMessage(message: String) {
        Snackbar.make(binding.coordLayout,
            message,
            Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun startShimmerAnimations() {
        binding.root.shimmer_details_container.startShimmerAnimation()
    }

    override fun hideShimmerPlaceholders() {
        binding.root.from_shimmer.hide()
        binding.root.to_shimmer.hide()
        binding.root.delivery_fee_shimmer.hide()
    }

    override fun stopShimmerAnimations() {
        binding.root.shimmer_details_container.stopShimmerAnimation()
    }
}