package com.atdev.courmata.ui.watchcourse

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atdev.courmata.R
import com.atdev.courmata.pojo.utilities.Constants
import com.atdev.courmata.pojo.utilities.ads_loader.AdsLoader
import com.atdev.courmata.pojo.utilities.ads_loader.LoadAllAds
import kotlinx.android.synthetic.main.activity_watch_course.*


class WatchCourse : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_course)
        //
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
        //
        loadURL()

        AdsLoader.LoadAdaptiveBannerAds(this@WatchCourse, banner_frame)
        showFullAds()
    }

    private fun loadURL() {

        val url = intent.getStringExtra("url")
        webView.getSettings().setJavaScriptEnabled(true) // enable javascript
        webView.setWebViewClient(object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(this@WatchCourse, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView,
                req: WebResourceRequest,
                rerr: WebResourceError
            ) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(
                    view,
                    rerr.errorCode,
                    rerr.description.toString(),
                    req.url.toString()
                )
            }
        })
        webView.loadUrl(url)
    }

    private fun showFullAds() {

        //Value Of Ads for Interitial Screen
        Constants.ADS_SHOWN_COUNTER++
        Log.d("AdsTEst", Constants.ADS_SHOWN_COUNTER.toString() + "")
        if (Constants.ADS_SHOWN_COUNTER % 4 == 0) {
            // Load Ads Full Screen
            LoadAllAds.LoadFullScreenAds(applicationContext)
            //Value Of Ads for Interitial Screen
            Log.d("AdsTEst", Constants.ADS_SHOWN_COUNTER.toString() + "")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)

    }
}
