package com.hfm.instad

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.JavascriptInterface

class InstaWebViewInterface(private val context: Context){
    @JavascriptInterface
    fun downloadLink(link:String){
        print("DownloadLink is $link ")
        //Toast.makeText(context,"DownloadLink is $link ", Toast.LENGTH_SHORT).show();
        val fileNameRegex=Regex("""/[a-zA-Z0-9_]+\.(mp4|jpg)""")
        val filename:String=fileNameRegex.find(link)?.value?.substring(1) ?: "instalDl"
        val downloadReq= DownloadManager.Request(Uri.parse(link)).setTitle("Downloading Insta Media")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"InstaDls/$filename")
        val dlManager=context.getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager
        dlManager?.enqueue(downloadReq)
    }
}