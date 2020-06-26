package com.example.mobilechallenge.data.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import com.example.mobilechallenge.data.local.dao.ItemCartDao
import com.example.mobilechallenge.data.models.Banner
import com.example.mobilechallenge.data.models.Game
import com.example.mobilechallenge.data.models.ItemCart
import com.example.mobilechallenge.data.remote.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepositoryImpl(
    @NonNull private val api: Api,
    @NonNull private val dao: ItemCartDao
) : Repository {

    private val composite = CompositeDisposable()

    override fun getAllBanners(success: (List<Banner>) -> Unit, failed: (Throwable) -> Unit) {
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

    override fun getAllGames(success: (List<Game>) -> Unit, failed: (Throwable) -> Unit) {
        val disposable = api.getAllGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                success(res)
            }, { error ->
                failed(error)
            })

        composite.add(disposable)
    }

    override fun getGameById(id: Int, success: (Game) -> Unit, failed: (Throwable) -> Unit) {
        val disposable = api.getGameById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                success(res)
            }, { error ->
                failed(error)
            })

        composite.add(disposable)
    }

    override suspend fun insertItemCart(itemCart: ItemCart) {
        dao.insertItemCar(itemCart)
    }

    override suspend fun deleteItemCart(itemCart: ItemCart) {
        dao.deleteItemCart(itemCart)
    }

    override suspend fun getItemCart(id: Int): ItemCart? {
        return dao.getItemCart(id)
    }

    override fun getAllItemsCart(): LiveData<List<ItemCart>> {
        return dao.getAllItemsCart()
    }

    override fun clearCompositeDisposable() {
        composite.clear()
    }
}