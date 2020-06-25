package com.example.mobilechallenge.view.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.R
import com.example.mobilechallenge.view.adapters.BannerAdapter
import com.example.mobilechallenge.view.adapters.HandlerAdapter
import com.example.mobilechallenge.view.factory.MainFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HandlerAdapter {

    @Inject
    lateinit var factory: MainFactory
    lateinit var viewModel: MainViewModel
    lateinit var bannerAdapter: BannerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as MyApplication).getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        bannerAdapter = BannerAdapter()
        bannerAdapter.setHandler(this)

        recycler_banner.hasFixedSize()
        recycler_banner.adapter = bannerAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.getBanners().observe(this, Observer { banners ->
            bannerAdapter.setItemList(banners)
        })
    }

    override fun onClickItem(view: View, position: Int) {

    }
}