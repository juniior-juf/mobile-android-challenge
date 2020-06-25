package com.example.mobilechallenge.view.ui.main

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilechallenge.data.models.Banner
import com.example.mobilechallenge.data.repositories.BannerRepository

class MainViewModel(@NonNull private val repoBanner: BannerRepository) : ViewModel() {

    private var banners = MutableLiveData<List<Banner>>()

    init {
        banners.value = emptyList()
        fetchAllBanners()
    }

    fun getBanners() = banners

    private fun fetchAllBanners() {
        repoBanner.getBanners({ res ->
            Log.d("JUF", "Success ${res.size}")
            banners.value = res
        }, { error ->
            error.printStackTrace()
        })
    }
}
