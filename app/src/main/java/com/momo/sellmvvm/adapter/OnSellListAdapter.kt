package com.momo.sellmvvm.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.momo.sellmvvm.R
import com.momo.sellmvvm.domain.MapData

class OnSellListAdapter : RecyclerView.Adapter<OnSellListAdapter.InnerHolder>() {

    private val mContentList = arrayListOf<MapData>()

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_on_sell, parent, false)
        return InnerHolder(itemView)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.itemView.apply {
            with(mContentList[position]){
                findViewById<TextView>(R.id.itemTitleTv).text = title
                findViewById<TextView>(R.id.off_price_tv).text = String.format("￥%.2f", zk_final_price.toFloat() - coupon_amount)
                Glide.with(context).load("https:$pict_url").into(findViewById(R.id.item_cover_iv))
            }
        }
    }

    override fun getItemCount(): Int {
        return mContentList.size
    }

    fun setData(it: List<MapData>) {
        mContentList.clear()
        mContentList.addAll(it)
        notifyDataSetChanged()
    }
}