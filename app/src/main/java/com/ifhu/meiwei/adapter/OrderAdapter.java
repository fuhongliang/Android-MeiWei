package com.ifhu.meiwei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.OrderBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends BaseAdapter {
    List<OrderBean> orderBeanList;
    OnClickItem onClickItem;
    Context mContext;

    public OrderAdapter(List<OrderBean> orderBeanList, Context mContext) {
        this.orderBeanList = orderBeanList;
        this.mContext = mContext;
    }

    public void setOrderBeanList(List<OrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
        notifyDataSetChanged();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem {
        void shopHomePage(int position);

        void orderDetail(int position);

        void evaluation(int position);

        void oneMore(int position);
    }

    @Override
    public int getCount() {
        return orderBeanList == null ? 0 : orderBeanList.size();
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
            convertView = layoutInflater.inflate(R.layout.item_order, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivPhoto.load(orderBeanList.get(position).getStore_avatar());
        viewHolder.tvStoreName.setText(orderBeanList.get(position).getStore_name());

        switch (orderBeanList.get(position).getOrder_state()) {
            case 1://"订单已取消";
                viewHolder.tvStatus.setText("订单已取消");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.hint_text_color));
                viewHolder.tvOneMore.setVisibility(View.GONE);
                viewHolder.tvEvaluation.setText("逛逛别家");
                viewHolder.tvEvaluation.setTextColor(mContext.getResources().getColor(R.color.black));
                viewHolder.tvEvaluation.setBackgroundResource(R.drawable.bg_gray);
                break;
            case 2://"待支付";
                viewHolder.tvStatus.setText("待支付");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.main_color));
                break;
            case 3://"等待商家接单";
                viewHolder.tvStatus.setText("已付款");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
                break;
            case 4://"商家已接单，正准备商品";
                viewHolder.tvStatus.setText("商家已接单");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
                break;
            case 5://"骑手正赶往商家";
                viewHolder.tvStatus.setText("等待骑手接单");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.main_color));
                break;
            case 6://"骑手正在送货";
                viewHolder.tvStatus.setText("骑手已接单");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.main_color));
                break;
            case 7://"订单已完成";
                viewHolder.tvStatus.setText("订单已完成");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.hint_text_color));
                break;
            case 8://:退款中
                break;
            case 9://退款已完成
                viewHolder.tvStatus.setText("退款成功");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.hint_text_color));
                viewHolder.tvOneMore.setText("再来一单");
                viewHolder.tvEvaluation.setText("退款详情");
                break;
            case 10://"待评价";
                viewHolder.tvEvaluation.setVisibility(View.VISIBLE);
                break;
            case 11://"已评价";
                break;
            default:
                break;
        }

        if (orderBeanList.get(position).getGoods_list().size() > 1) {
            viewHolder.tvProductName.setText(orderBeanList.get(position).getGoods_list().get(0).getGoods_name() + " 等" + orderBeanList.get(position).getGoods_list().size() + "件商品");
        } else {
            viewHolder.tvProductName.setText(orderBeanList.get(position).getGoods_list().get(0).getGoods_name());
        }

        viewHolder.tvMoney.setText(" ¥ " + orderBeanList.get(position).getTotal_amount() + "");

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_photo)
        GlideImageView ivPhoto;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.ll_store_name)
        LinearLayout llStoreName;
        @BindView(R.id.iv_line)
        ImageView ivLine;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.ll_product_name)
        LinearLayout llProductName;
        @BindView(R.id.tv_evaluation)
        TextView tvEvaluation;
        @BindView(R.id.tv_one_more)
        TextView tvOneMore;
        @BindView(R.id.rl_announcement)
        RelativeLayout rlAnnouncement;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
