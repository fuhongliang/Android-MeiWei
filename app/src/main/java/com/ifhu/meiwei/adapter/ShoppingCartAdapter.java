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
import com.ifhu.meiwei.bean.ShoppingCartBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShoppingCartAdapter extends BaseAdapter {

    List<ShoppingCartBean> shoppingCartBeanList;
    Context mContext;
    OnClickItem onClickItem;
    ClickEvent clickEvent;

    public void setClickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    public ShoppingCartAdapter(List<ShoppingCartBean> shoppingCartBeanList, Context mContext) {
        this.shoppingCartBeanList = shoppingCartBeanList;
        this.mContext = mContext;
    }

    public void setShoppingCartBeanList(List<ShoppingCartBean> shoppingCartBeanList) {
        this.shoppingCartBeanList = shoppingCartBeanList;
        notifyDataSetChanged();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem {
        void Settlement(int position);

    }

    @Override
    public int getCount() {
        return shoppingCartBeanList == null ? 0 : shoppingCartBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 购物车列表数据
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_shopping_cart, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(shoppingCartBeanList.get(position).getStore().getStore_name());
        viewHolder.tvCurrentPrice.setText(shoppingCartBeanList.get(position).getAmount());
        getCommodity(shoppingCartBeanList.get(position).getList(), viewHolder.llCommodity, shoppingCartBeanList.get(position).isExpend());

        if (shoppingCartBeanList.get(position).isExpend()) {
            viewHolder.rlExpand.setVisibility(View.GONE);
        } else {
            viewHolder.rlExpand.setVisibility(View.VISIBLE);
            viewHolder.tvExpand.setText("展开其他" + (shoppingCartBeanList.get(position).getList().size() - 2) + "商品");
        }

        viewHolder.rlExpand.setOnClickListener(v -> {
            shoppingCartBeanList.get(position).setExpend(true);
            notifyDataSetChanged();
        });
        viewHolder.ivDelete.setOnClickListener(v -> {
            if (clickEvent != null) {
                clickEvent.clearMerchantGoods();
            }
        });
        viewHolder.tvSettlement.setOnClickListener(v -> onClickItem.Settlement(position));

        return convertView;
    }

    /**
     * 获取商品列表数据
     */
    public void getCommodity(List<ShoppingCartBean.ListBean> gcsort_data, LinearLayout linearLayout, boolean isExpend) {
        linearLayout.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if (gcsort_data != null && gcsort_data.size() > 0) {
            if (isExpend) {
                for (int i = 0; i < gcsort_data.size(); i++) {
                    View categoryView = layoutInflater.inflate(R.layout.item_commodity, null);
                    TextView tvProductName = categoryView.findViewById(R.id.tv_product_name);
                    GlideImageView ivAvatar = categoryView.findViewById(R.id.iv_avatar);
                    TextView tvNumber = categoryView.findViewById(R.id.tv_number);
                    TextView tvMoney = categoryView.findViewById(R.id.tv_money);
                    ivAvatar.load(gcsort_data.get(i).getGoods_image());
                    tvProductName.setText(gcsort_data.get(i).getGoods_name());
                    tvNumber.setText(gcsort_data.get(i).getGoods_num() + "");
                    tvMoney.setText(gcsort_data.get(i).getGoods_price());
                    linearLayout.addView(categoryView);
                }
            } else {
                for (int i = 0; i < gcsort_data.size() && i < 2; i++) {
                    View categoryView = layoutInflater.inflate(R.layout.item_commodity, null);
                    TextView tvProductName = categoryView.findViewById(R.id.tv_product_name);
                    GlideImageView ivAvatar = categoryView.findViewById(R.id.iv_avatar);
                    TextView tvNumber = categoryView.findViewById(R.id.tv_number);
                    TextView tvMoney = categoryView.findViewById(R.id.tv_money);
                    ivAvatar.load(gcsort_data.get(i).getGoods_image());
                    tvProductName.setText(gcsort_data.get(i).getGoods_name());
                    tvNumber.setText(gcsort_data.get(i).getGoods_num() + "");
                    tvMoney.setText(gcsort_data.get(i).getGoods_price());
                    linearLayout.addView(categoryView);
                }
            }
        }

    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.ll_commodity)
        LinearLayout llCommodity;
        @BindView(R.id.tv_expand)
        TextView tvExpand;
        @BindView(R.id.rl_expand)
        RelativeLayout rlExpand;
        @BindView(R.id.tv_original_price)
        TextView tvOriginalPrice;
        @BindView(R.id.tv_total_price)
        TextView tvCurrentPrice;
        @BindView(R.id.tv_settlement)
        TextView tvSettlement;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface ClickEvent {
        void clearMerchantGoods();
    }
}
