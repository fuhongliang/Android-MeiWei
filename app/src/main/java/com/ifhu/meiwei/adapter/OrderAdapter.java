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
import com.ifhu.meiwei.bean.OrderBean;
import com.ifhu.meiwei.utils.Constants;

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
        void editAddress(int position);
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
            convertView = layoutInflater.inflate(R.layout.item_order, null);  //加载视图
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivPhoto.load(Constants.IMGPATH + orderBeanList.get(position).getStore_avatar());//商店头像
        viewHolder.tvStoreName.setText(orderBeanList.get(position).getStore_name());//商店名称
        switch (orderBeanList.get(position).getOrder_state()) {                     //订单状态
            case 0:
                viewHolder.tvStatus.setText("已取消");
                break;
            case 1:
                viewHolder.tvStatus.setText("未支付");
                break;
            case 2:
                viewHolder.tvStatus.setText("已付款");
                break;
            case 3:
                viewHolder.tvStatus.setText("商家已接单");
                break;
            case 4:
                viewHolder.tvStatus.setText("已发货");
                break;
            case 5:
                viewHolder.tvStatus.setText("骑手已接单");
                break;
            case 6:
                viewHolder.tvStatus.setText("已收货");
                break;

            default:
                break;
        }

//        viewHolder.tvProductName.setText(.get(0).getGoods_name());
        viewHolder.tvMoney.setText(" ¥ " + orderBeanList.get(position).getTotal_amount() + "");//总价格


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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
