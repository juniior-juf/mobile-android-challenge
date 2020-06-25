package com.example.mobilechallenge.data.remote

import com.example.mobilechallenge.data.models.Banner
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("banners")
    fun getBanners(): Single<List<Banner>>

}