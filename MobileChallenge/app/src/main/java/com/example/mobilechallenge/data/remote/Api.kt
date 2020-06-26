package com.example.mobilechallenge.data.remote

import com.example.mobilechallenge.data.models.Banner
import com.example.mobilechallenge.data.models.Game
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("banners")
    fun getBanners(): Single<List<Banner>>

    @GET("spotlight")
    fun getAllGames(): Single<List<Game>>

    @GET("games/{id}")
    fun getGameById(@Path("id") id: Int): Single<Game>

    @POST("checkout")
    fun checkout(@Body data: JsonObject): Completable
}