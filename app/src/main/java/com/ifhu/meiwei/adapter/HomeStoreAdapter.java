package com.ifhu.meiwei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.HomeBean;
import com.ifhu.meiwei.utils.FullReductionUtils;
import com.ifhu.meiwei.utils.StarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author KevinFu
 * @date 2019-05-28
 * Copyright (c) 2019 KevinFu
 */
public class HomeStoreAdapter extends BaseAdapter {
    public Context mContext;
    private List<HomeBean.StorelistDataBean> storelist_data = new ArrayList<>();

    public HomeStoreAdapter(Context context, List<HomeBean.StorelistDataBean> storelist_data) {
        mContext = context;
        this.storelist_data = storelist_data;
    }

    public void setStorelist_data(List<HomeBean.StorelistDataBean> storelist_data) {
        this.storelist_data = storelist_data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return storelist_data != null ? storelist_data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_home_shop, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvCommodityName.setText(storelist_data.get(position).getStore_name());
        viewHolder.mIvCommodity.load(storelist_data.get(position).getStore_avatar(), R.drawable.home_zanwutupian, 4);
        StarUtils.showStar(viewHolder.mLlStar, storelist_data.get(position).getStore_credit(), 5, LayoutInflater.from(mContext));
        viewHolder.mTvSaleNumber.setText("月售:" + storelist_data.get(position).getStore_sales());
        FullReductionUtils.showHomeFullReduction(viewHolder.mLlReduction,storelist_data.get(position).getManjian(),LayoutInflater.from(mContext));
        if (storelist_data.get(position).getXianshi() != null && storelist_data.get(position).getXianshi().size()>0){
            viewHolder.mTvLimitDiscounts.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mTvLimitDiscounts.setVisibility(View.GONE);
        }
        if (storelist_data.get(position).getXianshi() != null && storelist_data.get(position).getManjian().size()>0){
            viewHolder.mTvFullReduction.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mTvFullReduction.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_commodity)
        GlideImageView mIvCommodity;
        @BindView(R.id.tv_commodity_name)
        TextView mTvCommodityName;
        @BindView(R.id.ll_star)
        LinearLayout mLlStar;
        @BindView(R.id.iv_line)
        ImageView mIvLine;
        @BindView(R.id.tv_limit_discounts)
        TextView mTvLimitDiscounts;
        @BindView(R.id.tv_full_reduction)
        TextView mTvFullReduction;
        @BindView(R.id.tv_sale_number)
        TextView mTvSaleNumber;
        @BindView(R.id.tv_away)
        TextView mTvAway;
        @BindView(R.id.tv_shipping_info)
        TextView mTvShippingInfo;
        @BindView(R.id.ll_reduction)
        LinearLayout mLlReduction;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
