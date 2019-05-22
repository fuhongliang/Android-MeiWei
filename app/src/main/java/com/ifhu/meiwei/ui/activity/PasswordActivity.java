package com.ifhu.meiwei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.StringUtils;
import com.ifhu.meiwei.utils.ToastHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordActivity extends BaseActivity {

    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.ll_protocol)
    LinearLayout llProtocol;
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login);
        ButterKnife.bind(this);
        tvText.setText("验证码登录");
        //判断输入是否填写、填写完成才能点击按钮
        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLogin.setEnabled(checkPhoneNumber());
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLogin.setEnabled(checkPhoneNumber());
            }
        });

    }


    //    判断是否为空
    public boolean checkPhoneNumber() {
        if (StringUtils.isEmpty(etNumber.getText().toString())) {
            ToastHelper.makeText("请输入手机号码").show();
            return false;
        }
        if (StringUtils.isEmpty(etPassword.getText().toString())) {
            ToastHelper.makeText("请输入密码").show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.rl_return)
    public void onRlReturnClicked() {
        finish();
    }

    @OnClick(R.id.tv_title)
    public void onTvTitleClicked() {
        startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
    }

    @OnClick(R.id.tv_number)
    public void onTvNumberClicked() {
    }

    @OnClick(R.id.et_number)
    public void onEtNumberClicked() {
    }

    @OnClick(R.id.tv_login)
    public void onTvLoginClicked() {

    }

    @OnClick(R.id.ll_protocol)
    public void onLlProtocolClicked() {
    }
}
