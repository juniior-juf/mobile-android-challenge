package com.example.mobilechallenge.view.ui.browser

import android.os.Bundle

interface BrowserBase {

    fun initWebView(url: String?)

    fun restoreWebView(savedInstanceState: Bundle?)
}