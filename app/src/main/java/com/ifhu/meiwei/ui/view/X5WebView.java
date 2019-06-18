package com.ifhu.meiwei.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.ClientCertRequest;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author fuhongliang
 */
public class X5WebView extends WebView {
	TextView title;
	private WebViewClient client = new WebViewClient() {
		/**
		 * 防止加载网页时调起系统浏览器
		 */
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	};

	@SuppressLint("SetJavaScriptEnabled")
	public X5WebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		this.setWebViewClient(client);
		initWebViewSettings();
		this.getView().setClickable(true);
	}

	private void initWebViewSettings() {
		WebSettings webSetting = this.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(true);
		 webSetting.setLoadWithOverviewMode(true);
		webSetting.setAppCacheEnabled(true);
		// webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		// webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

		// this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
		// settings 的设计
	}

	public X5WebView(Context arg0) {
		super(arg0);
		setBackgroundColor(85621);
	}

}
