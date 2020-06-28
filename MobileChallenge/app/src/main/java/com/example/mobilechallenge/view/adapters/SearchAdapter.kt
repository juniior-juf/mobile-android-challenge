package com.example.mobilechallenge.view.adapters

import com.example.mobilechallenge.R
import com.example.mobilechallenge.data.models.Game

class SearchAdapter(private val handler: HandlerAdapter) : BaseAdapter() {

    private var games = emptyList<Game>()

    fun setItemList(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
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