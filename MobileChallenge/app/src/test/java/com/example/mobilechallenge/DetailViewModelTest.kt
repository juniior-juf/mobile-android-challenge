package com.example.mobilechallenge

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mobilechallenge.data.models.Game
import com.example.mobilechallenge.data.models.ItemCart
import com.example.mobilechallenge.data.repositories.Repository
import com.example.mobilechallenge.view.ui.detail.DetailViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var game: Observer<Game>

    @Mock
    private lateinit var inCart: Observer<Boolean>

    @Mock
    val throwable = Throwable()

    @Mock
    val sqlException = SQLiteConstraintException()

    private fun createViewModel(): DetailViewModel {
        return DetailViewModel(repository)
    }

    @Test
    fun `when fetch the game with a id, then return the game details`() {

        val gameDetail = Game(
            1,
            "title",
            "publisher",
            "www.image.com/img.jpg",
            100,
            200,
            "description",
            40F,
            4,
            50
        )

        val id = 1

        val success = argumentCaptor<(Game) -> Unit>()
        val failed = argumentCaptor<(Throwable) -> Unit>()

        val viewModel = createViewModel()
        viewModel.getGame().observeForever(game)

        viewModel.fetchGameDetail(id)

        verify(repository, times(1)).getGameById(
            ArgumentMatchers.eq(id),
            success.capture(),
            failed.capture()
        )
        success.firstValue.invoke(gameDetail)

        verify(game).onChanged(gameDetail)
        Assert.assertEquals(viewModel.getGame().value?.id, id)
    }

    @Test
    fun `when fetch the game with a id invalid, then return a exception (Throwable) not found`() {

        val id = 1

        val success = argumentCaptor<(Game) -> Unit>()
        val failed = argumentCaptor<(Throwable) -> Unit>()

        val viewModel = createViewModel()
        viewModel.getGame().observeForever(game)

        viewModel.fetchGameDetail(id)

        verify(repository, times(1)).getGameById(
            ArgumentMatchers.eq(id),
            success.capture(),
            failed.capture()
        )
        failed.firstValue.invoke(throwable)
        verify(throwable).printStackTrace()
    }

    @Test
    fun `when verify if item it's in shopping cart with a id, then must return true`() {
        val spy = spy(repository)
        val item = ItemCart(1, "title", "www.image.com/img.jpg", 4, 100, 2)

        val id = 1

        val viewModel = createViewModel()
        viewModel.inCart.observeForever(inCart)

        viewModel.verifyItemContainsInCart(id)

        runBlocking {
            `when`(spy.getItemCart(eq(id))).thenReturn(item)
            val result = spy.getItemCart(id)

            Assert.assertEquals(item, result)
            Assert.assertEquals(id, result?.id)

            viewModel.inCart.postValue(true)
        }
        verify(inCart).onChanged(true)
    }

    @Test
    fun `when verify if item it's in shopping cart with a id invalid, then must return false`() {
        val spy = spy(repository)

        val id = 2

        val viewModel = createViewModel()
        viewModel.inCart.observeForever(inCart)

        viewModel.verifyItemContainsInCart(id)

        runBlocking {
            `when`(spy.getItemCart(eq(id))).thenReturn(null)
            val result = spy.getItemCart(id)

            Assert.assertNull(result)
        }
    }

    @Test
    fun `when insert item in shopping cart, then must return true`() {
        val item = ItemCart(1, "title", "www.image.com/img.jpg", 4, 100, 2)

        val viewModel = createViewModel()
        viewModel.inCart.observeForever(inCart)

        viewModel.insertOrDeleteItem(item)

        runBlocking {
            verify(repository, times(1)).insertItemCart(item)
        }
        verify(inCart).onChanged(true)
    }

}