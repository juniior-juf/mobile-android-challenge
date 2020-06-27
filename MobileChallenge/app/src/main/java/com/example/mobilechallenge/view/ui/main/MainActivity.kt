package com.example.mobilechallenge.view.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.R
import com.example.mobilechallenge.databinding.ActivityMainBinding
import com.example.mobilechallenge.view.adapters.BannerAdapter
import com.example.mobilechallenge.view.adapters.GameAdapter
import com.example.mobilechallenge.view.adapters.HandlerAdapter
import com.example.mobilechallenge.view.adapters.SearchAdapter
import com.example.mobilechallenge.view.factory.DefaultFactory
import com.example.mobilechallenge.view.ui.browser.BrowserActivity
import com.example.mobilechallenge.view.ui.detail.DetailActivity
import com.example.mobilechallenge.view.ui.shopping_cart.ShoppingCartActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.container_layout_default.*
import kotlinx.android.synthetic.main.container_layout_search.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainBase, HandlerAdapter {

    @Inject
    lateinit var factory: DefaultFactory
    lateinit var viewModel: MainViewModel
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var gameAdapter: GameAdapter
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.getAppComponent().inject(this)

        initViewModel()
        initDataBinding()
        initBannerAdapter()
        initGameAdapter()
        initSearchAdapter()
        setupSearchView()
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.setListener(this)
    }

    override fun initDataBinding() {
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        binding.listener = this
        binding.viewModel = viewModel
    }

    override fun initBannerAdapter() {
        bannerAdapter = BannerAdapter()
        bannerAdapter.setHandler(this)

        recycler_banner.hasFixedSize()
        recycler_banner.adapter = bannerAdapter
    }

    fun initSearchAdapter() {
        searchAdapter = SearchAdapter()
        searchAdapter.setHandler(this)

        recycler_search.hasFixedSize()
        recycler_search.addItemDecoration(
            DividerItemDecoration(
                recycler_search.context,
                LinearLayoutManager.VERTICAL
            )
        )
        recycler_search.adapter = searchAdapter
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

    private fun setupSearchView() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    layout_search.visibility = if (newText.isNotEmpty()) View.VISIBLE else View.GONE
                    if (newText.isNotEmpty()) viewModel.searchGames(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
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

        viewModel.getListSearchGames().observe(this, Observer { games ->
            searchAdapter.setItemList(games)
        })
    }

    override fun onClickCart() {
        startActivity(Intent(this, ShoppingCartActivity::class.java))
    }

    override fun onClickItem(view: View, position: Int) {
        when (view.id) {
            R.id.card_banner -> {
                navigateToBrowserScreen(viewModel.getBanners().value!![position].url)
            }
            R.id.card_game -> {
                navigateToDetailScreen(viewModel.getGames().value!![position].id)
            }
            R.id.item_search -> {
                navigateToDetailScreen(viewModel.getListSearchGames().value!![position].id)
            }
            else -> Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToBrowserScreen(url: String) {
        val intent = Intent(this, BrowserActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun navigateToDetailScreen(id: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun showMessageSearch(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}