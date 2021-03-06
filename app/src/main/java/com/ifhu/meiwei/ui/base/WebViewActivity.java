package com.ifhu.meiwei.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.utils.JsJavaBridge;
import com.ifhu.meiwei.utils.StringUtils;
import com.ifhu.meiwei.utils.UserLogic;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author fuhongliang
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.progress_web)
    ProgressBar mProgressWeb;
    @BindView(R.id.wv_view)
    WebView mWvView;
    boolean loadError = false;

    public static final int PROGRESS_MAX_VALUE = 100;
    public static final String URL = "url";
    public static final String FILE_NAME = "fileName";
    public static final String TITLE = "title";
    public static final String HTML_DATA = "html_data";
    public static final String ISNEEDTOKEN = "need_token";
    public static final String ISNEEDShare= "ISNEEDShare";
    public static final String ISNEEDPermission = "ISNEEDPermission";
    @BindView(R.id.ll_web_content)
    LinearLayout mLlWebContent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_return)
    TextView mTvReturn;
    @BindView(R.id.rl_return)
    RelativeLayout mRlReturn;
    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.iv_share_it)
    ImageView mIvShareIt;
    @BindView(R.id.iv_collection)
    ImageView mIvCollection;
    boolean isNeedShare = false;

    public static void startLoadAssetsHtml(Context context, String fileName, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(FILE_NAME, fileName);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    public static void startJoin(Context context, String url, String title, boolean isNeedPermission) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(ISNEEDPermission, isNeedPermission);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    public static void start(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }
    public static void start(boolean isNeedShare,Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        intent.putExtra(ISNEEDShare, isNeedShare);
        context.startActivity(intent);
    }

    public static void start(Context context, String url, String title, boolean isNeedToken) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        intent.putExtra(ISNEEDTOKEN, isNeedToken);
        context.startActivity(intent);
    }

    public static void start(Context context, String htmlData) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(HTML_DATA, htmlData);
        context.startActivity(intent);
    }

    public static void startWithHtmlData(Context context, String htmlData, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(HTML_DATA, htmlData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        showWebTitle();
        initWebView();
        loadData();
        if (getIntent().getBooleanExtra(ISNEEDShare,false)){
            initShareView();
        }
    }

    public void initShareView(){
        mIvShareIt.setVisibility(View.VISIBLE);
        mIvCollection.setVisibility(View.VISIBLE);
    }
    public void requestPermission() {
        AndPermission.with(WebViewActivity.this)
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (getIntent().getBooleanExtra(ISNEEDTOKEN, false)) {
                            loadURL(true);
                        } else {
                            loadURL(false);
                        }
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {

            }
        }).start();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        mWvView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("mailto:")) {
                    Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                mWvView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                loadError = true;
            }
        });
        WebSettings setting = mWvView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setAllowFileAccess(true);
        setting.setAppCacheEnabled(true);
        setting.setSupportMultipleWindows(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWvView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWvView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == PROGRESS_MAX_VALUE) {
                    mProgressWeb.setVisibility(View.GONE);
                } else {
                    mProgressWeb.setVisibility(View.VISIBLE);
                    mProgressWeb.setProgress(newProgress);
                    mWvView.setVisibility(View.INVISIBLE);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

    }

    public void showWebTitle() {
        mTvText.setVisibility(View.INVISIBLE);
        String title = getIntent().getStringExtra(TITLE);
        if (!StringUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    public void loadData() {
        loadError = false;
        String htmlData = getIntent().getStringExtra(HTML_DATA);
        String fileName = getIntent().getStringExtra(FILE_NAME);
        if (StringUtils.isEmpty(htmlData)) {
            if (StringUtils.isEmpty(fileName)) {
                if (getIntent().getBooleanExtra(ISNEEDPermission, false)) {
                    requestPermission();
                } else {
                    if (getIntent().getBooleanExtra(ISNEEDTOKEN, false)) {
                        loadURL(true);
                    } else {
                        loadURL(false);
                    }
                }

            } else {
                mWvView.loadUrl("file:///android_asset/" + fileName);
            }

        } else {
            mWvView.loadData(htmlData, "text/html; charset=UTF-8", null);
        }
    }


    public void loadURL(boolean needToken) {
        mWvView.addJavascriptInterface(new JsJavaBridge(WebViewActivity.this, mWvView), "$App");
        if (needToken) {
            HashMap<String, String> headers = new HashMap<String, String>();
            String dataToken = UserLogic.getUser().getToken();
            headers.put("token", dataToken);
            mWvView.loadUrl(getIntent().getStringExtra(URL), headers);
        } else {
            mWvView.loadUrl(getIntent().getStringExtra(URL));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWvView.canGoBack()) {
                mWvView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.rl_return)
    public void onViewClicked() {
        finish();
    }
}
