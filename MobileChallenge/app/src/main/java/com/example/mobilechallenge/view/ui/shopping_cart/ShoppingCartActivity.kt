package com.example.mobilechallenge.view.ui.shopping_cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.R
import com.example.mobilechallenge.databinding.ActivityShoppingCartBinding
import com.example.mobilechallenge.view.adapters.HandlerAdapter
import com.example.mobilechallenge.view.adapters.ItemCartAdapter
import com.example.mobilechallenge.view.factory.DefaultFactory
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import javax.inject.Inject

class ShoppingCartActivity : AppCompatActivity(), HandlerAdapter {

    @Inject
    lateinit var factory: DefaultFactory
    private lateinit var viewModel: ShoppingCarViewModel
    private lateinit var adapter: ItemCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyApplication.getAppComponent().inject(this)

        initViewModel()
        initDataBinding()
        initAdapter()
        setupToolbar()
        setupRecycler()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)[ShoppingCarViewModel::class.java]
    }

    private fun initDataBinding() {
        val binding = DataBindingUtil.setContentView<ActivityShoppingCartBinding>(
            this,
            R.layout.activity_shopping_cart
        )
        binding.viewModel = viewModel
    }

    private fun initAdapter() {
        adapter = ItemCartAdapter()
        adapter.setHandler(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecycler() {
        recycler_items.setHasFixedSize(true)
        recycler_items.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        observableViewModel()
    }

    private fun observableViewModel() {
        viewModel.getItemsCart().observe(this, Observer { items ->
            adapter.setItemList(items)
            viewModel.calculateShoppingCart()
        })
    }

    override fun onClickItem(view: View, position: Int) {
        when (view.id) {
            R.id.imv_add -> viewModel.add(position)
            R.id.imv_decrease -> viewModel.decrease(position)
            R.id.imv_remove -> viewModel.removeItem(position)
        }
    }
}