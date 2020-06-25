package com.example.mobilechallenge.view.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.R
import com.example.mobilechallenge.view.adapters.BannerAdapter
import com.example.mobilechallenge.view.adapters.GameAdapter
import com.example.mobilechallenge.view.adapters.HandlerAdapter
import com.example.mobilechallenge.view.factory.MainFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainBase, HandlerAdapter {

    @Inject
    lateinit var factory: MainFactory
    lateinit var viewModel: MainViewModel
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as MyApplication).getAppComponent().inject(this)

        initViewModel()
        initBannerAdapter()
        initGameAdapter()
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun initBannerAdapter() {
        bannerAdapter = BannerAdapter()
        bannerAdapter.setHandler(this)

        recycler_banner.hasFixedSize()
        recycler_banner.adapter = bannerAdapter
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initGameAdapter() {
        gameAdapter = GameAdapter()
        gameAdapter.setHandler(this)

        recycler_game.hasFixedSize()
        recycler_game.adapter = gameAdapter
        recycler_game.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                layout_fab.visibility = View.GONE
            } else {
                layout_fab.visibility = View.VISIBLE
            }
            false
        }
    }

    override fun onStart() {
        super.onStart()
        observableViewModel()
    }

    override fun observableViewModel() {
        viewModel.getBanners().observe(this, Observer { banners ->
            bannerAdapter.setItemList(banners)
        })

        viewModel.getGames().observe(this, Observer { games ->
            gameAdapter.setItemList(games)
        })
    }

    override fun onClickItem(view: View, position: Int) {
        when (view.id) {
            R.id.card_banner -> {
                Toast.makeText(this, "Banner", Toast.LENGTH_SHORT).show()
            }
            R.id.card_game -> {
                Toast.makeText(this, "Game", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
        }
    }
}