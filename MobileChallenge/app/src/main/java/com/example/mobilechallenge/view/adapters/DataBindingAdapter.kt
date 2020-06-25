package com.example.mobilechallenge.view.adapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.mobilechallenge.R
import com.squareup.picasso.Picasso

class DataBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: ImageView, url: String?) {
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_image)
                .fit()
                .centerCrop()
                .into(view)
        }
    }
}