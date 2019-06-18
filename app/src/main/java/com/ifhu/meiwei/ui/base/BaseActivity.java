package com.ifhu.meiwei.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.bugtags.library.Bugtags;
import com.ifhu.meiwei.ui.loading.LoadingDialog;
import com.ifhu.meiwei.utils.ActivityCollector;
import com.ifhu.meiwei.utils.Utils;

import java.util.List;

import static com.ifhu.meiwei.utils.Constants.DATA;

/**
 *
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public class BaseActivity  extends AppCompatActivity {
    /**
     * 跳转到页面 cls
     * @param cls 目标页面
     */
    public void goToActivity(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }


    /**
     * 携带数据跳转
     * @param cls 目标页面
     * @param data 携带的数据，DATA
     */
    public void goToActivity(Class<?> cls,String data){
        Intent intent = new Intent(this,cls);
        intent.putExtra(DATA,data);
        startActivity(intent);
    }

    /**
     * 携带数据跳转
     * @param cls 目标页面
     * @param data 携带的数据，DATA
     */
    public void goToActivity(Class<?> cls,int data){
        Intent intent = new Intent(this,cls);
        intent.putExtra(DATA,data);
        startActivity(intent);
    }

    /**
     * 获取页面携带的字符串
     * @return 如果字符串为空则返回空
     */
    public int getDataInt(){
        return getIntent().getIntExtra(DATA,0);
    }

    /**
     * 获取页面携带的字符串
     * @return 如果字符串为空则返回空
     */
    public String getDATA(){
        if (getIntent().getStringExtra(DATA) == null){
            return "";
        }else {
            return getIntent().getStringExtra(DATA);

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    public void goBack(View view) {
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void finish() {
        super.finish();
    }
    /**
     * 是否显示加载提示
     * @param active 是否激活
     */
    public void setLoadingMessageIndicator(boolean active) {
        if (active) {
            LoadingDialog.progressShow(BaseActivity.this);
        } else {
            LoadingDialog.progressCancel();
        }
    }



    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .add(frameId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }


    /**
     * 替换fragment
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .replace(frameId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }


    /**
     * 隐藏fragment
     * @param fragment
     */
    protected void hideFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 显示fragment
     * @param fragment
     */
    protected void showFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 移除fragment
     * @param fragment
     */
    protected void removeFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 判断是否短时间内重复点击
     */

    private long lastClick = 0;
    public int twiceClickTime = 1500;

    public boolean isNotDuplication() {
        if (System.currentTimeMillis() - lastClick <= twiceClickTime) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    public void hideOrShowView(List<View> views, boolean hide){
        if (hide){
            for (View view:views){
                view.setVisibility(View.GONE);
            }
        }else {
            for (View view:views){
                view.setVisibility(View.VISIBLE);
            }
        }
    }

}
