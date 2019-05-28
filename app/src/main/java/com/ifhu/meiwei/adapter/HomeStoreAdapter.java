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
import com.ifhu.meiwei.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
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

        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_home_shop, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvCommodityName.setText(storelist_data.get(position).getStore_name());
        viewHolder.mIvCommodity.load(Constants.IMGPATH + storelist_data.get(position).getStore_avatar());
        return convertView;
    }

    static class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
