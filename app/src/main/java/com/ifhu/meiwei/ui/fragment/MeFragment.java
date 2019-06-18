package com.ifhu.meiwei.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.activity.home.ShoppingCartActivity;
import com.ifhu.meiwei.ui.activity.login.LoginActivity;
import com.ifhu.meiwei.ui.activity.me.MyAddressListActivity;
import com.ifhu.meiwei.ui.activity.me.MyAskActivity;
import com.ifhu.meiwei.ui.activity.me.MyCollectActivity;
import com.ifhu.meiwei.ui.activity.me.MyDiliverJoinActivity;
import com.ifhu.meiwei.ui.activity.me.MyHelpActivity;
import com.ifhu.meiwei.ui.activity.me.MyReviewActivity;
import com.ifhu.meiwei.ui.activity.me.MyShopJoinActivity;
import com.ifhu.meiwei.ui.activity.me.MyVoucherActivity;
import com.ifhu.meiwei.ui.activity.order.EvaluationActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.utils.UserLogic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的个人模块
 *
 * @author fuhongliang
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    Unbinder unbinder;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_account_info)
    RelativeLayout mRlAccountInfo;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    public void initView() {
        if (UserLogic.isLogin()) {
            tvName.setText(UserLogic.getUser().getMember_name());
            tvPhone.setText(UserLogic.getUser().getMember_mobile());
        } else {
            tvName.setText("点击登录");
            tvPhone.setText("暂无手机号码");
            mRlAccountInfo.setOnClickListener(v -> goToActivity(LoginActivity.class));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.ll_address, R.id.ll_voucher, R.id.ll_collect, R.id.ll_review, R.id.ll_help, R.id.ll_ask, R.id.ll_shop_join, R.id.ll_diliver_join, R.id.ll_settings})
    public void onViewClicked(View view) {
        if (UserLogic.isLogin()) {
            switch (view.getId()) {
                case R.id.ll_address://收货地址
                    goToActivity(MyAddressListActivity.class);
                    break;
                case R.id.ll_voucher://我的代金券
                    goToActivity(MyVoucherActivity.class);
                    break;
                case R.id.ll_collect://我的收藏
                    goToActivity(MyCollectActivity.class);
                    break;
                case R.id.ll_review://我的评价
                    goToActivity(MyReviewActivity.class);
                    break;
                case R.id.ll_help://帮助与反馈
                    goToActivity(MyHelpActivity.class);
                    break;
                case R.id.ll_ask://客服咨询
                    goToActivity(MyAskActivity.class);
                    break;
                case R.id.ll_shop_join://商家入驻
                    goToActivity(MyShopJoinActivity.class);
                    break;
                case R.id.ll_diliver_join://骑手入驻
                    goToActivity(MyDiliverJoinActivity.class);
                    break;
                case R.id.ll_settings://设置
//                    goToActivity(MySettingsActivity.class);
                    UserLogic.loginOut();
                    goToActivity(LoginActivity.class);
                    break;
                default:
                    break;
            }
        } else {
            goToActivity(LoginActivity.class);
        }

    }
}
