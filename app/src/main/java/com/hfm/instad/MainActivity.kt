package com.hfm.instad

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    val TAG="MainActivity"
    lateinit var  instaWebView:WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isStoragePermissionGranted()
        instaWebView=findViewById<WebView>(R.id.insta_web_view)
        instaWebView.settings.javaScriptEnabled=true
        instaWebView.settings.loadWithOverviewMode=true
        instaWebView.settings.useWideViewPort=true
        instaWebView.webViewClient= InstaWebViewClient()
        instaWebView.addJavascriptInterface(InstaWebViewInterface(this),"Android")
        instaWebView.loadUrl("http://instagram.com")
    }
    private fun isStoragePermissionGranted():Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this,arrayOf( Manifest.permission.WRITE_EXTERNAL_STORAGE), 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(
                TAG,
                "Permission: " + permissions[0] + "was " + grantResults[0]
            )
            //resume tasks needing this permission
        }
    }

    override fun onBackPressed() {
        if ( instaWebView.canGoBack() )
            instaWebView.goBack()
        else
            super.onBackPressed()
    }
}



