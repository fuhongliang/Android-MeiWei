package com.ifhu.meiwei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.MyAddressBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressAdapter extends BaseAdapter {


    List<MyAddressBean> myAddressBeanList;
    OnClickItem onClickItem;
    Context mContext;

    boolean needShowEditIcon = false;

    public MyAddressAdapter(List<MyAddressBean> myAddressBeanList, Context mContext, boolean needShowEditIcon) {
        this.myAddressBeanList = myAddressBeanList;
        this.mContext = mContext;
        this.needShowEditIcon = needShowEditIcon;
    }

    public MyAddressAdapter(List<MyAddressBean> myAddressBeanList, Context mContext) {
        this.myAddressBeanList = myAddressBeanList;
        this.mContext = mContext;
    }

    public void setmyAddressBeanList(List<MyAddressBean> myAddressBeanList) {
        this.myAddressBeanList = myAddressBeanList;
        //通知适配器更新数据
        notifyDataSetChanged();
    }



    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }


    /**
     * 钩子、接口
     */
    public interface OnClickItem {
        void editAddress(int position);
    }

    /**
     * 返回列表有多少项
     *
     * @return
     */
    @Override
    public int getCount() {
        return myAddressBeanList == null ? 0 : myAddressBeanList.size();
    }

    /**
     * 返回某一项
     * @param position 哪一项
     * @return
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     *  获取某一项ID
     * @param position 哪一项
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     *  返回每一项的视图
     * @param position 哪一项
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_my_address, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tvAddress.setText(myAddressBeanList.get(position).getArea_info());
        viewHolder.tvHouseNumber.setText(myAddressBeanList.get(position).getAddress());
        viewHolder.tvName.setText(myAddressBeanList.get(position).getTrue_name());
        viewHolder.tvGender.setText(myAddressBeanList.get(position).getSex() == 1? "男士" : "女士");
        viewHolder.tvPhone.setText(myAddressBeanList.get(position).getMob_phone());
        viewHolder.edit.setVisibility(needShowEditIcon? View.VISIBLE:View.INVISIBLE);

        if (onClickItem != null) {
            viewHolder.edit.setOnClickListener(v ->
                    onClickItem.editAddress(position));
        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_house_number)
        TextView tvHouseNumber;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_gender)
        TextView tvGender;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.edit)
        ImageView edit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
