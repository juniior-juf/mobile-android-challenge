package com.example.mobilechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mobilechallenge.data.models.Banner
import com.example.mobilechallenge.data.models.Game
import com.example.mobilechallenge.data.repositories.Repository
import com.example.mobilechallenge.view.ui.main.MainViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var banners: Observer<List<Banner>>

    @Mock
    private lateinit var games: Observer<List<Game>>

    @Mock
    val throwable = Throwable()

    private fun createViewModel(): MainViewModel {
        return MainViewModel(repository)
    }

    @Test
    fun `when fetch all banners then return a list of banners`() {
        val list = listOf(Banner(1, "image", "url"))
        val success = argumentCaptor<(List<Banner>) -> Unit>()
        val failed = argumentCaptor<(Throwable) -> Unit>()

        val viewModel = createViewModel()
        viewModel.getBanners().observeForever(banners)

        viewModel.fetchAllBanners()

        verify(repository, times(2)).getAllBanners(success.capture(), failed.capture())
        success.firstValue.invoke(list)

        verify(banners).onChanged(list)
    }

    @Test
    fun `when fetch all banners then return a list empty`() {
        val list = emptyList<Banner>()
        val success = argumentCaptor<(List<Banner>) -> Unit>()
        val failed = argumentCaptor<(Throwable) -> Unit>()

        val viewModel = createViewModel()

        viewModel.fetchAllBanners()

        verify(repository, times(2)).getAllBanners(success.capture(), failed.capture())
        success.firstValue.invoke(list)
    }

    @Test
    fun `when fetch all banners then return error server Throwable`() {

        val success = argumentCaptor<(List<Banner>) -> Unit>()
        val failed = argumentCaptor<(Throwable) -> Unit>()

        val viewModel = createViewModel()

        viewModel.fetchAllBanners()

        verify(repository, times(2)).getAllBanners(success.capture(), failed.capture())

        failed.firstValue.invoke(throwable)

        verify(throwable).printStackTrace()
    }

    @Test
    fun `When fetch all games then return list of games`() {

        val list = listOf(
            Game(
                1,
                "game 1",
                "produtora 1",
                "www.image.com.br/img.jpg",
                10,
                20,
                "description 1",
                3F,
                4,
                20
            )
        )

        val success = argumentCaptor<(List<Game>) -> Unit>()
        val failed = argumentCaptor<(Throwable) -> Unit>()

        val viewModel = createViewModel()
        viewModel.getGames().observeForever(games)

        viewModel.fetchAllGames()

        verify(repository, times(2)).getAllGames(success.capture(), failed.capture())
        success.firstValue.invoke(list)

        verify(games).onChanged(list)
    }

    @Test
    fun `When fetch all games then return list empty`() {
        val list = emptyList<Game>()
        val success = argumentCaptor<(List<Game>) -> Unit>()
        val failed = argumentCaptor<(Throwable) -> Unit>()

        val viewModel = createViewModel()

        viewModel.fetchAllGames()

        verify(repository, times(2)).getAllGames(success.capture(), failed.capture())
        success.firstValue.invoke(list)
    }

    @Test
    fun `When fetch all games then return error server Throwable`() {

        val success = argumentCaptor<(List<Game>) -> Unit>()
        val failed = argumentCaptor<(Throwable) -> Unit>()

        val viewModel = createViewModel()

        viewModel.fetchAllGames()

        verify(repository, times(2)).getAllGames(success.capture(), failed.capture())
        failed.firstValue.invoke(throwable)
    }
}