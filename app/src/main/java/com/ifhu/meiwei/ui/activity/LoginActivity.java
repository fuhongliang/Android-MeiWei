package com.ifhu.meiwei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.StringUtils;
import com.ifhu.meiwei.utils.ToastHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_verification_number)
    EditText etVerificationNumber;
    @BindView(R.id.tv_obtain_verification)
    TextView tvObtainVerification;
    @BindView(R.id.rl_verification)
    RelativeLayout rlVerification;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_third_party)
    TextView tvThirdParty;
    @BindView(R.id.ll_protocol)
    LinearLayout llProtocol;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    private int mCurSecond = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
                tvObtainVerification.setEnabled(checkPhonenumber());
            }
        });
        etVerificationNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLogin.setEnabled(checkPhonenumberAndMsgCode());
            }
        });
    }

    //    判断是否为空
    public boolean checkPhonenumber() {
        if (StringUtils.isEmpty(etNumber.getText().toString()) || etNumber.getText().toString().length() < 11) {
            icon();
            return false;
        }
        return true;
    }

    public boolean checkPhonenumberAndMsgCode() {
        if (StringUtils.isEmpty(etNumber.getText().toString()) || etNumber.getText().toString().length() < 11) {
            return false;
        }
        if (StringUtils.isEmpty(etVerificationNumber.getText().toString()) || etVerificationNumber.getText().toString().length() < 4) {
            return false;
        }
        return true;
    }

    //    获取验证码
    private void showCountDown() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(
                () -> {
                    mCurSecond--;
                    if (mCurSecond < 1) {
                        runOnUiThread(() -> {
                            tvObtainVerification.setText("获取验证码");
                            tvObtainVerification.setClickable(true);
                            executor.shutdown();
                        });
                        mCurSecond = 60;
                    } else {
                        String sencond = getString(R.string.get_code_again, mCurSecond);
                        runOnUiThread(() -> {
                            tvObtainVerification.setText(sencond);
                            tvObtainVerification.setClickable(false);
                        });
                    }
                },
                0,
                1000,
                TimeUnit.MILLISECONDS);
    }

    //判断手机号和验证码是否输入才能登陆
    public boolean checkContent(boolean isCheckCode) {
        if (StringUtils.isEmpty(etNumber.getText().toString().replaceAll(" ", ""))) {
            ToastHelper.makeText("请输入手机号码").show();
            return false;
        }
        if (isCheckCode) {
            if (StringUtils.isEmpty(etVerificationNumber.getText().toString().replaceAll(" ", ""))) {
                ToastHelper.makeText("请输入验证码").show();
                return false;
            }
        }
        return true;
    }

    //    删除icon显示与隐藏
    public void icon() {
        if (etNumber.length() > 0) {
            ivIcon.setVisibility(View.VISIBLE);
        } else {
            ivIcon.setVisibility(View.INVISIBLE);
        }
    }


    @OnClick(R.id.tv_text)
    public void onTvTextClicked() {
        startActivity(new Intent(LoginActivity.this, PasswordActivity.class));
    }


    @OnClick(R.id.tv_obtain_verification)
    public void onTvObtainVerificationClicked() {
        if (checkContent(true)) {

        }
    }

    @OnClick(R.id.tv_login)
    public void onTvLoginClicked() {
        if (checkContent(true)) {
            startActivity(new Intent(LoginActivity.this, SetpasswordActivity.class));
        }
    }

    @OnClick(R.id.iv_wechat)
    public void onIvWechatClicked() {
    }

    @OnClick(R.id.ll_protocol)
    public void onLlProtocolClicked() {
    }

    @OnClick(R.id.iv_icon)
    public void onIvIconClicked() {
        etNumber.setText("");
    }

    @OnClick(R.id.rl_return)
    public void onRlReturnClicked() {
        finish();
    }

}

