package com.example.englishpatterns.presentation.webConten

import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.lib.lokdroid.core.logD

@Composable
fun WebContentScreen(
    state: WebContentState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = CustomWebViewClient()
                    webChromeClient = CustomWebChromeClient()

                    settings.apply {
                        javaScriptEnabled = true
                    }

                    setWebContentsDebuggingEnabled(true)
                    loadUrl(state.url)
                }
            },
            update = { webView ->

            },
        )
    }
}

class CustomWebChromeClient() : WebChromeClient() {

    override fun onShowCustomView(view: View, callback: CustomViewCallback) {
        logD("onShowCustomView() called")
    }

    override fun onHideCustomView() {
        logD("onHideCustomView() called")
    }

    override fun onProgressChanged(view: WebView, progress: Int) {
        logD("onProgressChanged() called. progress: $progress")
    }

    override fun onPermissionRequest(request: PermissionRequest) {
        logD("onPermissionRequest() called. request: $request")
        logD("onPermissionRequest() called. request.resources: ${request.resources}")
    }

    override fun onShowFileChooser(
        webView: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams
    ): Boolean {
        logD("onShowFileChooser() called")
        return false
    }
}

class CustomWebViewClient() : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()

        logD("shouldOverrideUrlLoading() called. request url: $url")
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        logD("onPageStarted() called. url: $url")
    }

    override fun onLoadResource(view: WebView?, url: String) {
        logD("onLoadResource() called. url: $url")
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        logD("onPageFinished() called. url: $url")
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        logD("onReceivedError() called. url: ${request?.url}")
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        logD("onReceivedHttpError() called. url: ${request?.url}")
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        logD("onReceivedHttpError() called. url: ${error?.url}")
    }
}