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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    public void clearList() {
        this.orderBeanList = null;
        notifyDataSetChanged();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem {

        /**
         * 逛逛
         *
         * @param position
         */
        void shopping(int position);

        /**
         * 商家主页
         *
         * @param position
         */
        void shopHomePage(int position);

        /**
         * 订单详情
         *
         * @param position
         */
        void orderDetail(int position);

        /**
         * 评价
         *
         * @param position
         */
        void evaluation(int position);

        /**
         * 再来一单
         *
         * @param position
         */
        void oneMore(int position);

        /**
         * 立即支付
         *
         * @param position
         */
        void payOrder(int position);

        /**
         * 退款详情
         * @param position
         */
        void Return(int position);

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

        List<TextView> textViewList = new ArrayList<>();
        textViewList.add(viewHolder.tvOneMore);
        textViewList.add(viewHolder.tvShopping);
        textViewList.add(viewHolder.tvReturn);
        textViewList.add(viewHolder.tvEvaluation);
        textViewList.add(viewHolder.tvPay);

        switch (orderBeanList.get(position).getOrder_state()) {
            case 1://"订单已取消";
                viewHolder.tvStatus.setText("订单已取消");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.hint_text_color));
                showButtons(textViewList, true, false, false, false, false);
                break;
            case 2://"待支付";
                viewHolder.tvStatus.setText("待支付");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.main_color));
                viewHolder.tvEvaluation.setText("立即支付");
                showButtons(textViewList, false, false, false, false, true);

                break;
            case 3://"等待商家接单";
                viewHolder.tvStatus.setText("商家待接单");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
                showButtons(textViewList, true, false, false, false, false);

                break;
            case 4://"商家已接单，正准备商品";
                viewHolder.tvStatus.setText("商家已接单");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
                showButtons(textViewList, true, false, false, false, false);
                break;
            case 5://"骑手正赶往商家";
                viewHolder.tvStatus.setText("等待骑手接单");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.main_color));
                showButtons(textViewList, true, false, false, false, false);
                break;
            case 6://"骑手正在送货";
                viewHolder.tvStatus.setText("骑手已接单");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.main_color));
                showButtons(textViewList, true, false, false, false, false);
                break;
            case 7://"订单已完成";
                viewHolder.tvStatus.setText("订单已完成");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.hint_text_color));
                showButtons(textViewList, true, false, false, false, false);
                break;
            case 8://:退款中
                break;
            case 9://退款已完成
                viewHolder.tvStatus.setText("退款成功");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.hint_text_color));
                showButtons(textViewList, false, true, false, true, false);
                break;
            case 10://"待评价";
                viewHolder.tvStatus.setText("待评价");
                viewHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.main_color));
                showButtons(textViewList, true, false, true, false, false);
                break;
            case 11://"已评价";
                break;
            default:
                break;
        }


        viewHolder.tvOneMore.setOnClickListener(v -> onClickItem.oneMore(position));
        viewHolder.tvEvaluation.setOnClickListener(v -> onClickItem.evaluation(position));
        viewHolder.tvShopping.setOnClickListener(v -> onClickItem.shopping(position));
        viewHolder.rlAnnouncement.setOnClickListener(v -> onClickItem.orderDetail(position));
        viewHolder.tvPay.setOnClickListener(v -> onClickItem.payOrder(position));
        viewHolder.tvReturn.setOnClickListener(v -> onClickItem.Return(position));


        if (orderBeanList.get(position).getGoods_list().size() > 1) {
            viewHolder.tvProductName.setText(orderBeanList.get(position).getGoods_list().get(0).getGoods_name() + " 等" + orderBeanList.get(position).getGoods_list().size() + "件商品");
        } else {
            viewHolder.tvProductName.setText(orderBeanList.get(position).getGoods_list().get(0).getGoods_name());
        }

        viewHolder.tvMoney.setText(" ¥ " + orderBeanList.get(position).getTotal_amount() + "");
        if (onClickItem != null) {
            viewHolder.rlAnnouncement.setOnClickListener(v -> onClickItem.orderDetail(position));
            viewHolder.llStoreName.setOnClickListener(v -> onClickItem.shopHomePage(position));
            viewHolder.tvEvaluation.setOnClickListener(v -> onClickItem.evaluation(position));
        }

        return convertView;
    }


    public void showButtons(List<TextView> textViews, boolean oneMore, boolean shopping, boolean getBackMoney, boolean review, boolean pay) {
        for (int i = 0; i < textViews.size(); i++) {
            switch (i) {
                case 0:
                    textViews.get(i).setVisibility(oneMore ? View.VISIBLE : View.GONE);
                    break;
                case 1:
                    textViews.get(i).setVisibility(shopping ? View.VISIBLE : View.GONE);
                    break;
                case 2:
                    textViews.get(i).setVisibility(getBackMoney ? View.VISIBLE : View.GONE);
                    break;
                case 3:
                    textViews.get(i).setVisibility(review ? View.VISIBLE : View.GONE);
                    break;
                case 4:
                    textViews.get(i).setVisibility(pay ? View.VISIBLE : View.GONE);
                    break;
                default:
                    break;
            }
        }
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

        @BindView(R.id.tv_shopping)
        TextView tvShopping;
        @BindView(R.id.tv_return)
        TextView tvReturn;

        @BindView(R.id.tv_pay)
        TextView tvPay;

        @BindView(R.id.rl_announcement)
        RelativeLayout rlAnnouncement;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
