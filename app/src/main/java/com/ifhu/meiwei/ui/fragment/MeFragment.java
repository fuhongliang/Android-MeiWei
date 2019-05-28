package com.ifhu.meiwei.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.activity.login.LoginActivity;
import com.ifhu.meiwei.ui.activity.me.MyAddressListActivity;
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
        initView();

    }


    public void initView() {
        if (UserLogic.isLogin()) {
            tvName.setText(UserLogic.getUser().getMember_name());
            tvPhone.setText(UserLogic.getUser().getMember_mobile());
        } else {
            tvName.setText("未登录");
            tvPhone.setText("");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.ll_address, R.id.ll_voucher, R.id.ll_collect, R.id.ll_review, R.id.ll_help, R.id.ll_ask, R.id.ll_shop_join, R.id.ll_diliver_join, R.id.ll_settings})
    public void onViewClicked(View view) {
        if (UserLogic.isLogin()){
            switch (view.getId()) {
                case R.id.ll_address:
                    goToActivity(MyAddressListActivity.class);
                    break;
                case R.id.ll_voucher:

                    break;
                case R.id.ll_collect:
                    break;
                case R.id.ll_review:
                    break;
                case R.id.ll_help:
                    break;
                case R.id.ll_ask:
                    break;
                case R.id.ll_shop_join:
                    break;
                case R.id.ll_diliver_join:
                    break;
                case R.id.ll_settings:
                    break;
            }
        }else {
            goToActivity(LoginActivity.class);
        }

    }
}
