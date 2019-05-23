package com.ifhu.meiwei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.StringUtils;
import com.ifhu.meiwei.utils.ToastHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetpasswordActivity extends BaseActivity {
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);
        tvText.setVisibility(View.INVISIBLE);
        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLogin.setEnabled(checkPassword(etNumber,etPassword));
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
                tvLogin.setEnabled(checkPassword(etNumber,etPassword));
            }
        });

    }

    public boolean checkPassword(EditText etPassword,EditText etNewPassword) {
        if (StringUtils.isEmpty(etPassword.getText().toString())) {
            return false;
        }
        if (StringUtils.isEmpty(etNewPassword.getText().toString())) {
            return false;
        }
        return true;
    }

    public boolean isPassWord(String password,String anotherPassword) {
        if(StringUtils.isEmpty(password)){
            ToastHelper.makeText("密码不能为空，请重新输入").show();
            return false;
        }
        if (!password.equals(anotherPassword)) {
            ToastHelper.makeText("密码不一致，请重新输入").show();
            return false;
        }
        return true;
    }

    @OnClick(R.id.rl_return)
    public void onRlReturnClicked() {
        finish();
    }

    @OnClick(R.id.tv_login)
    public void onTvLoginClicked() {
        if (isPassWord(etNumber.getText().toString(),etPassword.getText().toString())){
            startActivity(new Intent(SetpasswordActivity.this,PasswordActivity.class));
        }
    }
}
