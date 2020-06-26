package com.example.mobilechallenge.view.adapters

import com.example.mobilechallenge.R
import com.example.mobilechallenge.data.models.ItemCart

class ItemCartAdapter : BaseAdapter() {

    private var items = emptyList<ItemCart>()
    private lateinit var handler: HandlerAdapter

    fun setItemList(items: List<ItemCart>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setHandler(handler: HandlerAdapter) {
        this.handler = handler
    }

    override fun getObjForPosition(position: Int): Any {
        return items[position]
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_cart
    }

    override fun getHandler(): HandlerAdapter? {
        return handler
    }

    override fun getItemCount(): Int {
        return items.size
    }
}