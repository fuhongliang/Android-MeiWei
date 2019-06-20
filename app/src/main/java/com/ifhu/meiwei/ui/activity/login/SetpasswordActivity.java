package com.ifhu.meiwei.ui.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.UserService;
import com.ifhu.meiwei.ui.activity.home.MainActivity;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.StringUtils;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置密码登录页面
 */
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
    @BindView(R.id.tv_return)
    TextView tvReturn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        tvReturn.setText("返回");
        ButterKnife.bind(this);
        tvText.setVisibility(View.INVISIBLE);
        /**
         * 判断输入是否填写、填写完成才能点击按钮
         */
        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLogin.setEnabled(checkPassword(etNumber, etPassword));
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
                tvLogin.setEnabled(checkPassword(etNumber, etPassword));
            }
        });

    }

    /**
     * 判断输入是否为空
     *
     * @param etPassword
     * @param etNewPassword
     * @return
     */
    public boolean checkPassword(EditText etPassword, EditText etNewPassword) {
        if (StringUtils.isEmpty(etPassword.getText().toString()) || etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 16) {
            return false;
        }
        if (StringUtils.isEmpty(etNewPassword.getText().toString()) || etNewPassword.getText().toString().length() < 6 || etNewPassword.getText().toString().length() > 16) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个密码算法输入一致
     *
     * @param password
     * @param anotherPassword
     * @return
     */
    public boolean isPassWord(String password, String anotherPassword) {
        if (StringUtils.isEmpty(password)) {
            ToastHelper.makeText("密码不能为空，请重新输入").show();
            return false;
        }
        if (!password.equals(anotherPassword)) {
            ToastHelper.makeText("密码不一致，请重新输入").show();
            return false;
        }
        return true;
    }

    /**
     * 设置密码接口
     */
    public void setPassword() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(UserService.class).userAddPwd(UserLogic.getUser().getMember_id() + "", etNumber.getText().toString())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                goToActivity(MainActivity.class);
            }

        });
    }

    @OnClick(R.id.rl_return)
    public void onRlReturnClicked() {
        finish();
    }

    @OnClick(R.id.tv_login)
    public void onTvLoginClicked() {
        if (isPassWord(etNumber.getText().toString(), etPassword.getText().toString())) {
            setPassword();
        }
    }
}
