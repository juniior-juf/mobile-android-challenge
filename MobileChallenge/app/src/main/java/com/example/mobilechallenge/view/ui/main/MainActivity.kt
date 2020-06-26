package com.example.mobilechallenge.view.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.R
import com.example.mobilechallenge.databinding.ActivityMainBinding
import com.example.mobilechallenge.view.adapters.BannerAdapter
import com.example.mobilechallenge.view.adapters.GameAdapter
import com.example.mobilechallenge.view.adapters.HandlerAdapter
import com.example.mobilechallenge.view.factory.DefaultFactory
import com.example.mobilechallenge.view.ui.browser.BrowserActivity
import com.example.mobilechallenge.view.ui.detail.DetailActivity
import com.example.mobilechallenge.view.ui.shopping_cart.ShoppingCartActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainBase, HandlerAdapter {

    @Inject
    lateinit var factory: DefaultFactory
    lateinit var viewModel: MainViewModel
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.getAppComponent().inject(this)

        initDataBinding()
        initViewModel()
        initBannerAdapter()
        initGameAdapter()
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun initDataBinding() {
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.listener = this
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

        viewModel.getCountItemsInCart().observe(this, Observer { itemsCart ->
            if (itemsCart.isEmpty()) {
                text_count.visibility = View.GONE
            } else {
                text_count.text = itemsCart.size.toString()
                text_count.visibility = View.VISIBLE
            }
        })

        viewModel.getGames().observe(this, Observer { games ->
            gameAdapter.setItemList(games)
        })
    }

    override fun onClickCart() {
        startActivity(Intent(this, ShoppingCartActivity::class.java))
    }

    override fun onClickItem(view: View, position: Int) {
        when (view.id) {
            R.id.card_banner -> navigateToBrowserScreen(position)
            R.id.card_game -> navigateToDetailScreen(position)
            else -> Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToBrowserScreen(position: Int) {
        val intent = Intent(this, BrowserActivity::class.java)
        intent.putExtra("url", viewModel.getBanners().value?.get(position)?.url)
        startActivity(intent)
    }

    private fun navigateToDetailScreen(position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", viewModel.getGames().value?.get(position)?.id)
        startActivity(intent)
    }
}