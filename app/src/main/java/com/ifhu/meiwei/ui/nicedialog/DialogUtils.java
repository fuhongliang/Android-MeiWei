package com.ifhu.meiwei.ui.nicedialog;

import android.support.v4.app.FragmentManager;

import com.ifhu.meiwei.bean.MerchantBean;

/**
 * @author KevinFu
 * @date 2019-06-10
 * Copyright (c) 2019 KevinFu
 */
public class DialogUtils {
    private DialogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void showExpandMerchantInfoDialog(String title, String message, FragmentManager manager, MerchantInfoExpandDialog.ButtonOnclick buttonOnclick) {
        MerchantInfoExpandDialog confirmDialog = MerchantInfoExpandDialog.newInstance(title, message);
        confirmDialog.setMargin(15);
        confirmDialog.setMarginTop(50);
        confirmDialog.setOutCancel(true);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showExpandMerchantInfoDialog(MerchantBean storeInfoBean, FragmentManager manager, MerchantInfoExpandDialog.ButtonOnclick buttonOnclick) {
        MerchantInfoExpandDialog confirmDialog = MerchantInfoExpandDialog.newInstance(storeInfoBean);
        confirmDialog.setMargin(15);
        confirmDialog.setMarginTop(50);
        confirmDialog.setOutCancel(true);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    /**
     * 弹出对话框
     *
     * @param title         标题
     * @param message       内容
     * @param manager       管理器
     * @param buttonOnclick 点击
     */
    public static void showConfirmDialog(String title, String message, FragmentManager manager, ConfirmDialog.ButtonOnclick buttonOnclick) {
        ConfirmDialog confirmDialog = ConfirmDialog.newInstance(title, message);
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showConfirmDialog(String title, String message, String cancel, String ok, FragmentManager manager, ConfirmDialog.ButtonOnclick buttonOnclick) {
        ConfirmDialog confirmDialog = ConfirmDialog.newInstance(title, message, cancel, ok);
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }
}
