package com.example.mobilechallenge.data.remote

import com.example.mobilechallenge.data.models.Banner
import com.example.mobilechallenge.data.models.Game
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("banners")
    fun getBanners(): Single<List<Banner>>

    @GET("spotlight")
    fun getGames():Single<List<Game>>

}