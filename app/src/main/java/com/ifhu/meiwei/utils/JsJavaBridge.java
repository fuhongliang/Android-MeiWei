package com.ifhu.meiwei.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.ifhu.meiwei.MyApplication;
import com.ifhu.meiwei.bean.MessageEvent;
import com.ifhu.meiwei.ui.base.WebViewActivity;
import com.ifhu.meiwei.ui.nicedialog.ConfirmDialog;
import com.ifhu.meiwei.ui.nicedialog.DialogUtils;
import com.ifhu.meiwei.ui.view.X5WebView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import static com.ifhu.meiwei.utils.Constants.LOCATION_DATAUPDATAFAIL;
import static com.ifhu.meiwei.utils.Constants.MERPHONENUMBER;

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


    /**
     * 选择城市
     * @param phoneNumber 返回的手机号码
     */
    @JavascriptInterface
    public void phoneCall(String phoneNumber) {
        Logger.d(phoneNumber);
        EventBus.getDefault().post(new MessageEvent(MERPHONENUMBER,phoneNumber));
    }
}
