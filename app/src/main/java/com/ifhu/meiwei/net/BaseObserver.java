package com.ifhu.meiwei.net;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;
import android.widget.Toast;

import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.MessageEvent;
import com.ifhu.meiwei.utils.ToastHelper;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.ifhu.meiwei.utils.Constants.LOGOUT;

/**
 *
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
    /**
     * 用于标识服务端返回异常时是否弹出错误toast
     */
    private boolean needShowErrorToast = false;

    public BaseObserver(boolean showErrorToast) {
        this.needShowErrorToast = showErrorToast;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }


    @Override
    public void onNext(BaseEntity<T> baseEntity) {
        /**
         * 成功或者失败都将打印接口返回数据
         */
        if (baseEntity.isSuccess()) {
            try {
                onSuccees(baseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(baseEntity);
                if (needShowErrorToast) {
                    if (!TextUtils.isEmpty(baseEntity.getMessage())) {
                        ToastHelper.makeText(baseEntity.getMessage()).show();
                    }
                }
                /**
                 * 如果Token失效将发通知退出APP
                 */
                if (baseEntity.isTokenTimeOut()) {
                    try {
                        EventBus.getDefault().post(new MessageEvent(LOGOUT));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException
                    || e instanceof SocketTimeoutException) {

                onFailure(e, true);
            } else {
                if (e instanceof HttpException) {
                    HttpException error = (HttpException) e;
                    if (400 <= error.code() && error.code() < 500) {
                        if (needShowErrorToast) {
                            if (!TextUtils.isEmpty(error.getMessage())) {
                                ToastHelper.makeText(error.getMessage()).show();
                            }
                        }
                    } else if (error.code() >= 500) {
                        onFailure(e, false);
                    }
                } else {
                    ToastHelper.makeText(e.getMessage()).show();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        onApiComplete();
    }

    @Override
    public void onComplete() {
        onApiComplete();
    }

    /**
     * 接口处理无论成功与否页面对用的操作，需复写此方法
     *
     * @param
     * @throws Exception
     */
    protected abstract void onApiComplete();

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(BaseEntity<T> t) throws Exception {
    }


    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
    }

}

