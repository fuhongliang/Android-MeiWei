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

		@Override
		public void onLoadResource(WebView view, String s) {
			super.onLoadResource(view, s);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
			return super.shouldOverrideUrlLoading(view, request);
		}

		@Override
		public void onPageStarted(WebView view, String s, Bitmap bitmap) {
			super.onPageStarted(view, s, bitmap);
		}

		@Override
		public void onPageFinished(WebView view, String s) {
			super.onPageFinished(view, s);
		}

		@Override
		public void onReceivedError(WebView view, int i, String s, String s1) {
			super.onReceivedError(view, i, s, s1);
		}

		@Override
		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
			super.onReceivedError(view, request, error);
		}

		@Override
		public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse response) {
			super.onReceivedHttpError(view, request, response);
		}

		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view, String s) {
			return super.shouldInterceptRequest(view, s);
		}

		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
			return super.shouldInterceptRequest(view, request);
		}

		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request, Bundle bundle) {
			return super.shouldInterceptRequest(view, request, bundle);
		}

		@Override
		public void doUpdateVisitedHistory(WebView view, String s, boolean b) {
			super.doUpdateVisitedHistory(view, s, b);
		}

		@Override
		public void onFormResubmission(WebView view, Message message, Message message1) {
			super.onFormResubmission(view, message, message1);
		}

		@Override
		public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String s, String s1) {
			super.onReceivedHttpAuthRequest(view, handler, s, s1);
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			super.onReceivedSslError(view, handler, error);
		}

		@Override
		public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
			super.onReceivedClientCertRequest(view, request);
		}

		@Override
		public void onScaleChanged(WebView view, float v, float v1) {
			super.onScaleChanged(view, v, v1);
		}

		@Override
		public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
			super.onUnhandledKeyEvent(view, event);
		}

		@Override
		public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
			return super.shouldOverrideKeyEvent(view, event);
		}

		@Override
		public void onTooManyRedirects(WebView view, Message message, Message message1) {
			super.onTooManyRedirects(view, message, message1);
		}

		@Override
		public void onReceivedLoginRequest(WebView view, String s, String s1, String s2) {
			super.onReceivedLoginRequest(view, s, s1, s2);
		}

		@Override
		public void onDetectedBlankScreen(String s, int i) {
			super.onDetectedBlankScreen(s, i);
		}
	};

	@SuppressLint("SetJavaScriptEnabled")
	public X5WebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
//		this.setWebViewClient(client);
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
