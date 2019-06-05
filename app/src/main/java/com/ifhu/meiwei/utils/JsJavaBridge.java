package com.ifhu.meiwei.utils;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.ifhu.meiwei.ui.base.WebViewActivity;
import com.ifhu.meiwei.ui.view.X5WebView;

/**
 * Created by KevinFu on 2019-06-04.
 * Copyright (c) 2019 KevinFu
 */
public class JsJavaBridge {
    private Activity activity;
    private X5WebView webView;

    public JsJavaBridge(Activity activity, X5WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    @JavascriptInterface
    public void onFinishActivity() {
        activity.finish();
    }

    @JavascriptInterface
    public void showLicensePage(String url) {
        ToastHelper.makeText(url).show();
//      WebViewActivity.start(getActivity(),url,"营业执照");
    }
}