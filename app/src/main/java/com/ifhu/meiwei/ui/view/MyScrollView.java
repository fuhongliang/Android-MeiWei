package com.ifhu.meiwei.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by KevinFu on 2019/5/17.
 * Copyright (c) 2019 KevinFu
 */
public class MyScrollView extends ScrollView {

    private OnScrollListener onScrollListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }

    /**
     * 接口对外公开
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_UP:
//                mHandler.sendEmptyMessageDelayed(0, 10);
//                break;
//                default:
//                    break;
//        }
//        return super.onTouchEvent(ev);
//    }

//  private int lastY = -1;
//  @SuppressLint("HandlerLeak")
//  private Handler mHandler = new Handler() {
//    @Override
//    public void handleMessage(Message msg) {
//      if (msg.what == 0) {
//        int scrollY = getScrollY();
//        if (scrollY == lastY) {
//            onScrollListener.onFinish(true);
//        } else {
//           lastY = scrollY;
//           mHandler.sendEmptyMessageDelayed(0, 10);
//        }
//      }
//    }
//  };

    /**
     *
     * 滚动的回调接口
     *
     * @author xiaanming
     *
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY
         *              、
         */
        void onScroll(int scrollY);

//        void onFinish(boolean isFinish);
    }
}
