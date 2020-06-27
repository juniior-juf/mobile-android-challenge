package com.example.mobilechallenge.view.adapters

import com.example.mobilechallenge.R
import com.example.mobilechallenge.data.models.Game

class SearchAdapter : BaseAdapter() {

    private var games = emptyList<Game>()
    private lateinit var handler: HandlerAdapter

    fun setItemList(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }

    fun setHandler(handler: HandlerAdapter) {
        this.handler = handler
    }

    override fun getObjForPosition(position: Int): Any {
        return games[position]
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_search
    }

    override fun getHandler(): HandlerAdapter? {
        return handler
    }

    override fun getItemCount(): Int {
        return games.size
    }
}