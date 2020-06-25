package com.example.mobilechallenge.view.adapters

import com.example.mobilechallenge.R
import com.example.mobilechallenge.data.models.Banner

class BannerAdapter : BaseAdapter() {

    private var banners = emptyList<Banner>()
    private lateinit var handler: HandlerAdapter

    fun setItemList(banners: List<Banner>) {
        this.banners = banners
        notifyDataSetChanged()
    }

    fun setHandler(handler: HandlerAdapter) {
        this.handler = handler
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