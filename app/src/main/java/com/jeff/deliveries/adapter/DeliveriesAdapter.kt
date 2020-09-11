package com.jeff.deliveries.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.jeff.deliveries.R
import com.jeff.deliveries.adapter.DeliveriesAdapter.CustomViewHolder
import com.jeff.deliveries.android.base.extension.hide
import com.jeff.deliveries.android.base.extension.show
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.database.local.Favorite
import com.jeff.deliveries.databinding.ItemDeliveryBinding
import com.jeff.deliveries.main.detail.view.DetailsActivity
import java.util.*
import kotlin.Comparator

internal class DeliveriesAdapter(
    private val context: Context,
    private val dataList: MutableList<Delivery>,
    private val favoriteList: List<Favorite>
) : RecyclerView.Adapter<CustomViewHolder>() {

    internal class CustomViewHolder(binding: ItemDeliveryBinding) :
        ViewHolder(binding.root) {
        var itemLayout: ConstraintLayout = binding.itemLayout
        var from: TextView = binding.from
        var to: TextView = binding.to
        var price: TextView = binding.price
        val thumbnail: ImageView = binding.thumbnail
        val favorite: TextView = binding.favorite
        val fromShimmer: View = binding.fromListShimmer
        val toShimmer: View = binding.toListShimmer
        val priceShimmer: View = binding.priceListShimmer

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<ItemDeliveryBinding>(
            LayoutInflater.from(p0.context),
            R.layout.item_delivery,
            p0,
            false
        )
        sort()
        return CustomViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = dataList[position]
        holder.from.text = item.route.start
        holder.to.text = item.route.end
        val deliveryFee: String = item.deliveryFee.replace("$", "")
        val surcharge: String = item.deliveryFee.replace("$", "")

        for (f in favoriteList) {
            if (f.id == item.id) {
                if (f.isFavorite) {
                    holder.favorite.show()
                } else {
                    holder.favorite.hide()
                }
            }
        }

        val price = String.format("$${surcharge.toFloat() + deliveryFee.toFloat()}")
        holder.price.text = price

        Glide
            .with(context)
            .load(item.goodsPicture)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_background)
            .into(holder.thumbnail)

        holder.itemLayout.setOnClickListener {
            val intent = DetailsActivity.getStartIntent(
                context,
                item.id,
                item.route.start,
                item.route.end,
                item.goodsPicture,
                price
            )
            context.startActivity(intent)
        }
    }

    private fun getPrice(s1: String, s2: String): Float {
        val deliveryFee = s1.replace("$", "")
        val surcharge = s2.replace("$", "")

        return surcharge.toFloat() + deliveryFee.toFloat()
    }

    private fun sort() {
        Collections.sort(dataList, Comparator<Delivery> { obj1, obj2 ->
            // ## Ascending order

            getPrice(obj2.deliveryFee, obj2.surcharge)
                .compareTo(getPrice(obj1.deliveryFee, obj1.surcharge)) // To compare string values
            // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

            // ## Descending order
            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
        })
    }

    fun addAll(users: MutableList<Delivery>) {
        dataList.addAll(users)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun getLastIndex(): Int {
        return dataList.lastIndex
    }
}