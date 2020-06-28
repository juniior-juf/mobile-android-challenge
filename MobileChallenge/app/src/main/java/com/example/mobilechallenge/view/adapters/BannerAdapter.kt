package com.example.mobilechallenge.view.adapters

import com.example.mobilechallenge.R
import com.example.mobilechallenge.data.models.Banner

class BannerAdapter(private val handler: HandlerAdapter) : BaseAdapter() {

    private var banners = emptyList<Banner>()

    fun setItemList(banners: List<Banner>) {
        this.banners = banners
        notifyDataSetChanged()
    }

    override fun getObjForPosition(position: Int): Any {
        return banners[position]
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_banner
    }

    override fun getHandler(): HandlerAdapter? {
        return handler
    }

    override fun getItemCount(): Int {
        return banners.size
    }
}