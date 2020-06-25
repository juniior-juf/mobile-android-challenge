package com.example.mobilechallenge.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class DataBindingAdapter {

    companion object {

        private val context: Context = MyApplication.getAppComponent().context()

        private const val LANGUAGE = "pt"
        private const val COUNTRY = "BR"

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: ImageView, url: String?) {
            if (url?.isNotEmpty()!!)
                Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(view)
        }

        @SuppressLint("SetTextI18n")
        @JvmStatic
        @BindingAdapter("currencyStrike")
        fun setCurrencyStrike(view: TextView, value: Int) {
            view.text = "de ${NumberFormat
                .getCurrencyInstance(Locale(LANGUAGE, COUNTRY))
                .format(BigDecimal.valueOf(value.toLong()))}"
            view.paintFlags = view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        @BindingAdapter("currency")
        @JvmStatic
        fun setCurrency(view: TextView, value: Int) {
            view.text = NumberFormat
                .getCurrencyInstance(Locale(LANGUAGE, COUNTRY))
                .format(BigDecimal.valueOf(value.toLong()))
        }

        @BindingAdapter("type")
        @JvmStatic
        fun setType(view: FloatingActionButton, value: Boolean) {
            val icon = if (value) R.drawable.ic_remove_cart else R.drawable.ic_cart
            val colorIcon = if (value) R.color.colorWhite else R.color.colorBlack32
            val background = if (value) R.color.colorAccent else R.color.colorWhite

            view.setImageDrawable(ContextCompat.getDrawable(context, icon))
            view.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, (colorIcon)))
            view.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, (background)))
        }
    }
}