package com.example.mobilechallenge.data.repositories

import androidx.annotation.NonNull
import com.example.mobilechallenge.data.models.Banner
import com.example.mobilechallenge.data.models.Game
import com.example.mobilechallenge.data.remote.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Repository(@NonNull private val api: Api) {

    private val composite = CompositeDisposable()

    fun getBanners(success: (List<Banner>) -> Unit, failed: (Throwable) -> Unit) {
        val disposable = api.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                success(res)
            }, { error ->
                failed(error)
            })

        composite.add(disposable)
    }

    fun getGames(success: (List<Game>) -> Unit, failed: (Throwable) -> Unit) {
        val disposable = api.getGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                success(res)
            }, { error ->
                failed(error)
            })

        composite.add(disposable)
    }

    fun clearCompositeDisposable() {
        composite.clear()
    }
}