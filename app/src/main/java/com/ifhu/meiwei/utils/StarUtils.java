package com.ifhu.meiwei.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ifhu.meiwei.R;

/**
 *
 * @author KevinFu
 * @date 2019-06-03
 * Copyright (c) 2019 KevinFu
 */
public class StarUtils {

    public static void showStar(LinearLayout layout, int selectedStar,int totalStar, LayoutInflater layoutInflater){
        layout.removeAllViews();
        if (totalStar > 0){
            for (int i = 0; i < totalStar; i++) {
                View mView = layoutInflater.inflate(R.layout.item_star, null);
                ImageView imageView = mView.findViewById(R.id.iv_star);
                if (i <= selectedStar-1){
                    imageView.setSelected(true);
                }else {
                    imageView.setSelected(false);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DeviceUtil.dip2px(11), DeviceUtil.dip2px(11));
                params.setMarginEnd(4);
                layout.addView(imageView,params);
            }
        }
    }
}
