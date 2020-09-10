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
import com.jeff.deliveries.database.local.Delivery
import com.jeff.deliveries.databinding.ItemDeliveryBinding

internal class DeliveriesAdapter(
    private val context: Context,
    private val dataList: MutableList<Delivery>
) : RecyclerView.Adapter<CustomViewHolder>() {

    internal class CustomViewHolder(binding: ItemDeliveryBinding) :
        ViewHolder(binding.root) {
        var itemLayout: ConstraintLayout = binding.itemLayout
        var from: TextView = binding.from
        var to: TextView = binding.to
        var price: TextView = binding.price
        val thumbnail: ImageView = binding.thumbnail
        val favorite: TextView = binding.favorite
        val fromShimmer: View = binding.fromShimmer
        val toShimmer: View = binding.toShimmer
        val priceShimmer: View = binding.priceShimmer

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<ItemDeliveryBinding>(
            LayoutInflater.from(p0.context),
            R.layout.item_delivery,
            p0,
            false
        )
        return CustomViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = dataList[position]
        holder.from.text = item.route.start
        holder.to.text = item.route.end
        val deliveryFee: String = item.deliveryFee.replace("$", "")
        val surcharge: String = item.deliveryFee.replace("$", "")

        /*if (favoriteList[position].id == item.id) {
            holder.favorite.show()
        } else {
            holder.favorite.hide()
        }*/

        holder.price.text = String.format("$${surcharge.toFloat() + deliveryFee.toFloat()}")

        Glide
            .with(context)
            .load(item.goodsPicture)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_background)
            .into(holder.thumbnail)

        holder.itemLayout.setOnClickListener {
            /*val intent = DetailsActivity.getStartIntent(
                context,
                item.id,
                item.title,
                item.url,
                item.thumbnailUrl
            )
            context.startActivity(intent)*/
        }
    }

    fun addAll(users: MutableList<Delivery>) {
        dataList.addAll(users)
        notifyDataSetChanged()
        notifyItemRangeInserted(dataList.lastIndex, users.size)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun getLastIndex(): Int {
        return dataList.lastIndex
    }
}