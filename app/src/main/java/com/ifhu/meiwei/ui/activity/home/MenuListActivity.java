package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.MenuAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.MenuBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MenuListActivity extends BaseActivity {
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_evaluation)
    TextView tvEvaluation;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_voucher)
    LinearLayout llVoucher;
    @BindView(R.id.rl_expand)
    RelativeLayout rlExpand;
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.lv_right)
    ListView lvRight;

    List<MenuBean> menuBeanList = new ArrayList<>();
    MenuAdapter menuAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        shopDetails();

    }

    /**
     * 店铺详情接口
     */

    public void shopDetails() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(HomeService.class).storeInfo("", UserLogic.getUser().getMember_id() + "", "", 1, 1)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<MenuBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }


        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    /**
     * 领取代金券接口
     */
    public void getVoucher() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(HomeService.class).getVoucher(getDataInt(), UserLogic.getUser().getMember_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }


        });
    }

    @OnClick(R.id.ll_voucher)
    public void onLlVoucherClicked() {
    }

    @OnClick(R.id.rl_expand)
    public void onRlExpandClicked() {
    }
}
