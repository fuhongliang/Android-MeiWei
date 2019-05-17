package com.ifhu.meiwei.net;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 线程调度器
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public class SchedulerUtils {
    public static ObservableTransformer ioMainScheduler() {
        return (Observable upstream) -> {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }
}
