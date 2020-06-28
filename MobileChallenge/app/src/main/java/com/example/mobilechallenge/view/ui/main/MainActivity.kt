package com.example.mobilechallenge.view.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class MainActivity : AppCompatActivity(), MainListener, HandlerAdapter {

    companion object {
        private val TAG = this::class.java.name
    }

    @Inject
    lateinit var factory: DefaultFactory
    private lateinit var viewModel: MainViewModel

    private val bannerAdapter: BannerAdapter by lazy { BannerAdapter(this) }
    private val gameAdapter: GameAdapter by lazy { GameAdapter(this) }
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.getAppComponent().inject(this)

        initViewModel()
        initDataBinding()
        setupRecyclerBanner()
        setupRecyclerGame()
        setupRecyclerSearch()
        setupSearchView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.setListener(this)
    }

    private fun initDataBinding() {
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        binding.listener = this
        binding.viewModel = viewModel
    }

    private fun setupRecyclerBanner() {
        recycler_banner.hasFixedSize()
        recycler_banner.adapter = bannerAdapter
    }

    private fun setupRecyclerSearch() {
        recycler_search.hasFixedSize()
        recycler_search.addItemDecoration(
            DividerItemDecoration(recycler_search.context, LinearLayoutManager.VERTICAL)
        )
        recycler_search.adapter = searchAdapter
    }

    private fun setupRecyclerGame() {
        recycler_game.hasFixedSize()
        recycler_game.setOnTouchListener(onTouchListener)
        recycler_game.adapter = gameAdapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = View.OnTouchListener { _, event ->
        layout_floating.visibility = when (event.action) {
            MotionEvent.ACTION_MOVE -> View.GONE
            else -> View.VISIBLE
        }
        false
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

    private fun observableViewModel() {
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
            else -> Log.w(TAG, "Invalid input")
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