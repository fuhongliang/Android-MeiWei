package com.ifhu.meiwei.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifhu.meiwei.ui.loading.LoadingDialog;
import com.ifhu.meiwei.utils.StringUtils;
import com.ifhu.meiwei.utils.Utils;

import static com.ifhu.meiwei.utils.Constants.DATA;

/**
 * @author fuhongliang
 */
@Keep
public abstract class BaseFragment extends Fragment {
    /**
     * 跳转到页面 cls
     * @param cls 目标页面
     */
    public void goToActivity(Class<?> cls){
        Intent intent = new Intent(getHoldingActivity(),cls);
        startActivity(intent);
    }


    /**
     * 携带数据跳转
     * @param cls 目标页面
     * @param data 携带的数据，DATA
     */
    public void goToActivity(Class<?> cls,String data){
        Intent intent = new Intent(getHoldingActivity(),cls);
        intent.putExtra(DATA,data);
        startActivity(intent);
    }

    /**
     * 携带数据跳转
     * @param cls 目标页面
     * @param data 携带的数据，DATA
     */
    public void goToActivity(Class<?> cls,int data){
        Intent intent = new Intent(getHoldingActivity(),cls);
        intent.putExtra(DATA,data);
        startActivity(intent);
    }

    protected BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }

    /**
     * 获取页面携带的字符串
     * @return 如果字符串为空则返回空
     */
    public String getDATA(){
        if (getHoldingActivity().getIntent().getStringExtra(DATA) == null){
            return "";
        }else {
            return getHoldingActivity().getIntent().getStringExtra(DATA);

        }
    }

    /**
     * 获取页面携带的字符串
     * @return 如果字符串为空则返回空
     */
    public int getDataInt(){
      return getHoldingActivity().getIntent().getIntExtra(DATA,0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 获取宿主Activity
     *
     * @return BaseActivity
     */
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }


    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().addFragment(fragment, frameId);

    }


    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().replaceFragment(fragment, frameId);
    }


    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    protected void hideFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().hideFragment(fragment);
    }


    /**
     * 显示fragment
     *
     * @param fragment
     */
    protected void showFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().showFragment(fragment);
    }


    /**
     * 移除Fragment
     *
     * @param fragment
     */
    protected void removeFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().removeFragment(fragment);

    }


    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        getHoldingActivity().popFragment();
    }

    /**
     * 是否显示加载提示
     * @param active 是否激活
     */
    public void setLoadingMessageIndicator(boolean active) {
        if (active) {
            LoadingDialog.progressShow(getHoldingActivity());
        } else {
            LoadingDialog.progressCancel();
        }
    }

    /**
     * 判断是否短时间内重复点击
     */
    private long lastClick = 0;
    public int twiceClickTime = 2500;

    public boolean isNotDuplication() {
        if (System.currentTimeMillis() - lastClick <= twiceClickTime) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
}
