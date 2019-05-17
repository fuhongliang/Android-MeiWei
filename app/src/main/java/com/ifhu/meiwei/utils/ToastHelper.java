package com.ifhu.meiwei.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ifhu.meiwei.MyApplication;
import com.ifhu.meiwei.R;

/**
 *
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public class ToastHelper extends AbstractToast {
    private static ToastHelper toastHelper;
    private Context mContext;
    private TextView mTextView;
    /**
     * 正常Toast类型
     */
    public static final int NORMALTOAST = 0;
    /**
     * 成功并且带有图标
     */
    public static final int SUCCESSWITHICONTOAST = 2;
    /**
     * 失败加上图标
     */
    public static final int FAILWITHICONTOAST = 3;
    /**
     * 警告
     */
    public static final int WARNWITHICONTOAST = 1;

    /**
     * 只提示文字
     */
    public static final int ONLYWORDTOAST = 4;

    /**
     * 当前样式
     */
    public static int CURRENTTOAST = 0;

    /**
     * 构造行数
     * @param context 上下文
     * @param style toast类型
     */
    public ToastHelper(Context context, int style){
        super(context);
        mContext = context;
        init(style);
    }

    /**
     * 根据样式进行加载不同的布局文件进行初始化
     * @param style toast样式
     */
    private void init(int style){
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.toast_normal_layout, null, false);
        CURRENTTOAST = style;
        mTextView = view.findViewById(R.id.tv_message);
        setView(view);
        setGravity(Gravity.CENTER, 0, 0);
        setDuration(Toast.LENGTH_LONG);
    }

    /**
     * 创建ToastHelper
     * @param text toast文本
     * @param duration 时间长短
     * @param style 样式
     * @return
     */
    public static ToastHelper makeText(CharSequence text, int duration, int style) {
        text = TextUtils.isEmpty(text)?"":text;
        reset();
        toastHelper = new ToastHelper(MyApplication.getApplication(),style);
        toastHelper.setText(text);
        toastHelper.setDuration(duration);
        return toastHelper;
    }
    /**
     * 创建ToastHelper
     * @param text toast文本
     * @return
     */
    public static ToastHelper makeText(CharSequence text) {
        text = TextUtils.isEmpty(text)?"":text;
        reset();
        toastHelper = new ToastHelper(MyApplication.getApplication(),NORMALTOAST);
        toastHelper.setText(text);
        toastHelper.setDuration(Toast.LENGTH_SHORT);
        return toastHelper;
    }
    /**
     * 设置toast内容
     * @param s 文本内容
     */
    @Override
    public void setText(CharSequence s) {
        mTextView.setText(s);
    }

    /**
     * toast show
     */
    @Override
    public void show() {
        String message = mTextView.getText().toString();
        if (!TextUtils.isEmpty(message) && toastHelper != null ) {
            super.show();
        }
    }

    public static void reset() {
        toastHelper = null;
    }
}

