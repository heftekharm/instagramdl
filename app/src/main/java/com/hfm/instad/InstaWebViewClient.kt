package com.hfm.instad

import android.webkit.WebView
import android.webkit.WebViewClient


class InstaWebViewClient: WebViewClient() {
    private val scripts="""
        
        var tempScrollY = window.scrollY;
        addDlBtns();
        window.addEventListener('scroll', function cb(e) {
            if (Math.abs(window.scrollY - tempScrollY) > 400) {
                addDlBtns();
                tempScrollY = window.scrollY;
            }
        }
        )


        function addDlBtns() {
            let ltpmrSections = document.getElementsByClassName('ltpMr');
            let instaBtnRaw = '<button class="wpO6b " type="button" onclick="dlBtnClick(this)" style=" padding: 0; margin-left: 10px; "><svg aria-label="Share Post" class="_8-yf5 " fill="#262626" height="24" viewBox="0 0 500 500" width="24" style=" stroke: red; "><g style=" stroke: red; "> <circle cx="250" cy="250" r="235" fill="transparent" style=" stroke: black; stroke-width: 28; stroke-opacity: 0.8; "></circle> <path d="M253.9,360.4l68.9-68.9c4.8-4.8,4.8-12.5,0-17.3s-12.5-4.8-17.3,0l-48,48V138.7c0-6.8-5.5-12.3-12.3-12.3 s-12.3,5.5-12.3,12.3v183.4l-48-48c-4.8-4.8-12.5-4.8-17.3,0s-4.8,12.5,0,17.3l68.9,68.9c2.4,2.4,5.5,3.6,8.7,3.6 S251.5,362.8,253.9,360.4z"></path> </g></svg></button>';
            for (let sec of ltpmrSections) {
                if (sec.lastChild.tagName == "SPAN") {
                    let instaDlBtn = document.createElement('div');
                    instaDlBtn.innerHTML = instaBtnRaw;
                    instaDlBtn = instaDlBtn.firstChild;
                    sec.appendChild(instaDlBtn);
                }
            }
        }
        //first check wether the post is album or not.Album posts contain li tags with "Ckrof" as classname 
        //single picture
        //single video

        //if Back Button is enabled
        function dlBtnClick(btn) {
            let btnGrandUncle = btn.parentElement.parentElement.previousElementSibling;
            let src = "";
            let mediaAncestor;
            let lis = btnGrandUncle.getElementsByClassName("Ckrof");
            if (lis.length > 0) {
                let li = btnGrandUncle.getElementsByClassName("POSa_").length == 0 ? lis[0] : lis[1];
                mediaAncestor = li;
            } else {
                mediaAncestor = btnGrandUncle;
            }
            let img = mediaAncestor.querySelector('img.FFVAD');
            if (img != null) {
                let srcset = img.getAttribute('srcset');
                let srcs = srcset.split(',');
                let lastSrc = srcs.pop();
                src = lastSrc.split(' ')[0];
                console.log(src);
            } else {
                let video = mediaAncestor.querySelector('video.tWeCl');
                if (video != null) {
                    src = video.src;
                    console.log(src);
                } else {
                    return;
                }
            }
            if (typeof Android != "undefined") {
                Android.downloadLink(src);
            }

        }
    """.trimIndent()





    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.evaluateJavascript(scripts,null)

    }
}