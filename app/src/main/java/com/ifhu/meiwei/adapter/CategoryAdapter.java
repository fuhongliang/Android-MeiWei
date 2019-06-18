package com.ifhu.meiwei.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.MerchantBean;

import java.util.List;

/**
 * Created by KevinFu on 2019-06-04.
 * Copyright (c) 2019 KevinFu
 */
public class CategoryAdapter  extends BaseAdapter {
    public List<MerchantBean.GoodsListBean> mDataList;
    public Context mContext;
    public int mCurPosition = 0;
    ItemOnclick itemOnclick;

    public CategoryAdapter(List<MerchantBean.GoodsListBean> mDataList, Context mContext,  ItemOnclick itemOnclick) {
        this.mDataList = mDataList;
        this.mContext = mContext;
        this.itemOnclick = itemOnclick;
    }

    public void setmDataList(List<MerchantBean.GoodsListBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public void setCurPosition(int curPosition) {
        mCurPosition = curPosition;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
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
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_category, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.iv_line);
            viewHolder.imgIcon = convertView.findViewById(R.id.iv_icon);
            viewHolder.textView = convertView.findViewById(R.id.tv_categroy);
            viewHolder.frameLayout = convertView.findViewById(R.id.ll_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position==0){
            viewHolder.imgIcon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgIcon.setVisibility(View.GONE);
        }

        if (mCurPosition == position) {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.textView.setTextColor(mContext.getResources().getColor(R.color.black));
            viewHolder.frameLayout.setBackgroundColor(Color.WHITE);
        } else {
            viewHolder.frameLayout.setBackgroundColor(mContext.getResources().getColor(R.color.category_color));
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.textView.setTextColor(mContext.getResources().getColor(R.color.navigation_color));
        }
        viewHolder.textView.setText(mDataList.get(position).getStc_name());
        viewHolder.frameLayout.setOnClickListener(v -> {
            mCurPosition = position;
            itemOnclick.onClickItem(position);
        });

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView imgIcon;
        FrameLayout frameLayout;
    }

    public interface ItemOnclick {
        /**
         * 点击事件
         * @param position 位置
         */
        void onClickItem(int position);
    }
}
