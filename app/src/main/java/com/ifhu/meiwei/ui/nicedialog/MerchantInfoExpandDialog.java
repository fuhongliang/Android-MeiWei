package com.ifhu.meiwei.ui.nicedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.MerchantBean;
import com.ifhu.meiwei.utils.FullReductionUtils;


/**
 * @author fuhongliang
 */
public class MerchantInfoExpandDialog extends BaseNiceDialog {
    public ButtonOnclick buttonOnclick;
    public String title;
    public String message;
    public String ok;
    public MerchantBean storeInfo;

    public static MerchantInfoExpandDialog newInstance(MerchantBean storeInfoBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",storeInfoBean);
        MerchantInfoExpandDialog dialog = new MerchantInfoExpandDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static MerchantInfoExpandDialog newInstance(String title, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        MerchantInfoExpandDialog dialog = new MerchantInfoExpandDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static MerchantInfoExpandDialog newInstance(String title, String message, String cancel, String ok) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putString("ok", ok);
        MerchantInfoExpandDialog dialog = new MerchantInfoExpandDialog();
        dialog.setArguments(bundle);
        return dialog;
    }
    public void setButtonOnclick(ButtonOnclick buttonOnclick) {
        this.buttonOnclick = buttonOnclick;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        title = bundle.getString("title");
        message = bundle.getString("message");
        ok = bundle.getString("ok");
        storeInfo = bundle.getParcelable("data");
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_menu_expand;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
//        holder.setText(R.id.title, title);
//        holder.setText(R.id.message, message);
//        if (!StringUtils.isEmpty(ok)){
//            holder.setText(R.id.ok, ok);
//        }
//
        if (storeInfo!=null){
            if (storeInfo.getManjian()==null || storeInfo.getManjian().size()<=0){
                (holder.getView(R.id.ll_reduction)).setVisibility(View.GONE);
            } else {
                FullReductionUtils.showShopFullReduction((holder.getView(R.id.tv_full_cut)), storeInfo.getManjian(), LayoutInflater.from(getActivity()));
            }
            ((TextView)holder.getView(R.id.tv_store_name)).setText(storeInfo.getStore_info().getStore_name());
            ((TextView)holder.getView(R.id.tv_merchant_describe)).setText("起送￥15｜配送￥3｜月售 " + storeInfo.getStore_info().getStore_sales());
            ((TextView)holder.getView(R.id.tv_announcement_content)).setText("公告"+storeInfo.getStore_info().getStore_description());
            ((TextView)holder.getView(R.id.tv_evaluation)).setText(storeInfo.getStore_info().getStore_credit()+"\n"+"评分");
            ((GlideImageView)holder.getView(R.id.iv_store_img)).load(storeInfo.getStore_info().getStore_avatar());
        }

        holder.setOnClickListener(R.id.iv_close, v -> {
            dialog.dismiss();
//            if (buttonOnclick != null){
//                EditText editText = holder.getView(R.id.dialog_edit);
//                buttonOnclick.ok(editText.getText().toString());
//            }
        });
    }


    public interface ButtonOnclick{
        void ok(String discount_price);
    }
}