package com.ifhu.meiwei.utils;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.ifhu.meiwei.ui.base.WebViewActivity;
import com.ifhu.meiwei.ui.view.X5WebView;
import com.orhanobut.logger.Logger;

/**
 * Created by KevinFu on 2019-06-04.
 * Copyright (c) 2019 KevinFu
 */
public class JsJavaBridge {
    private Activity activity;
    private X5WebView webView;
    private WebView mWebView;

    public JsJavaBridge(Activity activity, X5WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    public JsJavaBridge(Activity activity, WebView webView) {
        this.activity = activity;
        this.mWebView = webView;
    }

    @JavascriptInterface
    public void onFinishActivity() {
        activity.finish();
    }

    @JavascriptInterface
    public void showLicensePage(String url) {
        WebViewActivity.start(activity, url, "营业执照");
    }

    /**
     * 选择城市
     * @param city 返回的城市
     */
    @JavascriptInterface
    public void chooseCity(String city) {
        Logger.d(city);
    }
}
