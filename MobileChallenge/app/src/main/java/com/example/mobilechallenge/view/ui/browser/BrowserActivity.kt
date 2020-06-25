package com.example.mobilechallenge.view.ui.browser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobilechallenge.R

class BrowserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}