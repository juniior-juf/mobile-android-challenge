package com.example.mobilechallenge.view.ui.browser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.mobilechallenge.R
import kotlinx.android.synthetic.main.activity_browser.*

class BrowserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = intent?.extras?.get("url")?.toString()

        if (savedInstanceState == null)
            initWebView(url)
        else
            restoreWebView(savedInstanceState)
    }

    private fun initWebView(url: String?) {
        web_view.loadUrl(url)
        web_view.webChromeClient = webClient
    }

    private val webClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress == 100) {
                web_view.visibility = View.VISIBLE
                progress_circular.visibility = View.GONE
            }
        }
    }

    private fun restoreWebView(savedInstanceState: Bundle?) {
        web_view.restoreState(savedInstanceState?.getBundle("webViewState"))
        web_view.visibility = View.VISIBLE
        progress_circular.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()
        web_view.saveState(bundle)
        outState.putBundle("webViewState", bundle)
    }
}