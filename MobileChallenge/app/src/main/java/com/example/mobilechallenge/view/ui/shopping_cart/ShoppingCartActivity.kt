package com.example.mobilechallenge.view.ui.shopping_cart

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.R
import com.example.mobilechallenge.databinding.ActivityShoppingCartBinding
import com.example.mobilechallenge.view.adapters.HandlerAdapter
import com.example.mobilechallenge.view.adapters.ItemCartAdapter
import com.example.mobilechallenge.view.factory.DefaultFactory
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import javax.inject.Inject

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartListener, HandlerAdapter {

    @Inject
    lateinit var factory: DefaultFactory
    private lateinit var viewModel: ShoppingCarViewModel

    private val adapter: ItemCartAdapter by lazy { ItemCartAdapter(this) }
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyApplication.getAppComponent().inject(this)

        initViewModel()
        initDataBinding()
        setupToolbar()
        setupRecycler()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)[ShoppingCarViewModel::class.java]
        viewModel.setListener(this)
    }

    private fun initDataBinding() {
        val binding = DataBindingUtil.setContentView<ActivityShoppingCartBinding>(
            this,
            R.layout.activity_shopping_cart
        )
        binding.viewModel = viewModel
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecycler() {
        recycler_items.setHasFixedSize(true)
        recycler_items.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        observableViewModel()
    }

    private fun observableViewModel() {
        viewModel.getItemsCart().observe(this, Observer { items ->
            adapter.setItemList(items)
            viewModel.calculateShoppingCart()
        })
    }

    override fun onClickItem(view: View, position: Int) {
        when (view.id) {
            R.id.imv_add -> viewModel.increaseItemQuantity(position)
            R.id.imv_decrease -> viewModel.decreaseItemQuantity(position)
            R.id.imv_remove -> viewModel.removeItem(position)
        }
    }

    override fun showProgressDialogCheckout() {
        val view = LayoutInflater.from(this).inflate(R.layout.container_progress, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)

        dialog = builder.create()
        dialog?.show()
    }

    override fun successCheckout() {
        dialog?.hide()
        showDialogSuccessCheckout()
    }

    override fun showDialogSuccessCheckout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Compra finalizada")
        builder.setMessage("Sua compra foi finalizada com sucesso!")
        builder.setPositiveButton("OK") { _, _ ->
            dialog?.hide()
            finish()
        }

        dialog = builder.create()
        dialog?.show()

        dialog?.getButton(DialogInterface.BUTTON_POSITIVE)
            ?.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    override fun failedCheckout() {
        dialog?.hide()
        Toast.makeText(
            this,
            "Ocorreu um problema durante a finalização da compra.\nTente novamente!",
            Toast.LENGTH_LONG
        ).show()
    }
}