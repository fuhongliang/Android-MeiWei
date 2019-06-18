package com.ifhu.meiwei.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.HomeBean;
import com.ifhu.meiwei.bean.MerchantBean;

import java.util.List;

/**
 *
 * @author KevinFu
 * @date 2019-06-03
 * Copyright (c) 2019 KevinFu
 */
public class FullReductionUtils {

    public static void showShopFullReduction(LinearLayout mLlFullCut, List<MerchantBean.ManjianBean> manjianBeanList, LayoutInflater layoutInflater){
        mLlFullCut.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DeviceUtil.dip2px(17));
        params.rightMargin = DeviceUtil.dip2px(10);
        if (manjianBeanList != null && manjianBeanList.size()>0){
                for (MerchantBean.ManjianBean manjianBean:manjianBeanList){
                    View view = layoutInflater.inflate(R.layout.item_shop_full_cut,null);
                    TextView textView = view.findViewById(R.id.tv_full_cut);
                    textView.setText(manjianBean.getPrice()+"减"+manjianBean.getDiscount()+"");
                    mLlFullCut.addView(view,params);
                }
            }else {
                mLlFullCut.setVisibility(View.GONE);
        }
    }

    public static void showHomeFullReduction(LinearLayout mLlFullCut, List<HomeBean.StorelistDataBean.ManjianBean> manjianBeanList, LayoutInflater layoutInflater){
        mLlFullCut.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DeviceUtil.dip2px(17));
        params.rightMargin = DeviceUtil.dip2px(10);
        if (manjianBeanList != null && manjianBeanList.size()>0){
            for (HomeBean.StorelistDataBean.ManjianBean manjianBean:manjianBeanList){
                View view = layoutInflater.inflate(R.layout.item_shop_full_cut,null);
                TextView textView = view.findViewById(R.id.tv_full_cut);
                textView.setText(manjianBean.getPrice()+"减"+manjianBean.getDiscount()+"");
                mLlFullCut.addView(view,params);
            }
        }else {
            mLlFullCut.setVisibility(View.GONE);
        }
    }
}
