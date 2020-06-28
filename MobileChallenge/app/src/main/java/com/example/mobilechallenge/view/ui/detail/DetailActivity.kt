package com.example.mobilechallenge.view.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.R
import com.example.mobilechallenge.databinding.ActivityDetailBinding
import com.example.mobilechallenge.view.factory.DefaultFactory
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: DefaultFactory

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.getAppComponent().inject(this)

        val id = intent?.extras?.get("id") as Int

        initViewModel(id)
        initDataBinding()
        setupToolbar()
    }

    private fun initViewModel(id: Int) {
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        viewModel.setId(id)
    }

    private fun initDataBinding() {
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(
            this,
            R.layout.activity_detail
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}