package com.ifhu.meiwei.ui.nicedialog;

import android.support.v4.app.FragmentManager;

/**
 *
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
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }
}
