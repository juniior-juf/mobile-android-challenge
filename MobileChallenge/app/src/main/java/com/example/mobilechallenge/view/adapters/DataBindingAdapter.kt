package com.example.mobilechallenge.view.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mobilechallenge.R
import com.squareup.picasso.Picasso
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class DataBindingAdapter {

    companion object {

        private const val LANGUAGE = "pt"
        private const val COUNTRY = "BR"

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: ImageView, url: String?) {
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
    }
}