package com.ifhu.meiwei.ui.activity.login;

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
import android.widget.Toast;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.UserBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.UserService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.StringUtils;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 验证码登录页面
 */
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
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_return)
    TextView mTvReturn;
    @BindView(R.id.tv_verification)
    TextView mTvVerification;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.iv_expand)
    ImageView mIvExpand;
    @BindView(R.id.rl_number)
    RelativeLayout mRlNumber;
    @BindView(R.id.iv_wechat)
    ImageView mIvWechat;
    private int mCurSecond = 60;
    boolean isCodeLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        addTextChangedListener();
        initView();
    }

    public void initView() {
        if (isCodeLogin) {
            tvText.setText("密码登录");
            mTvVerification.setText("验证码登录");
            tvObtainVerification.setVisibility(View.VISIBLE);
            etVerificationNumber.setHint("请输入验证码");
        } else {
            tvText.setText("验证码登录");
            mTvVerification.setText("密码登录");
            tvObtainVerification.setVisibility(View.GONE);
            etVerificationNumber.setHint("请输入密码");
        }
    }


    /**
     * 判断输入是否填写、填写完成才能点击按钮
     */
    public void addTextChangedListener() {
        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvObtainVerification.setEnabled(checkContent(false));
                tvLogin.setEnabled(checkContent(true));
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
                tvLogin.setEnabled(checkContent(true));
            }
        });
    }


    /**
     * 开始倒计时
     */
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

    /**
     * 获取验证码接口
     */
    public void getVerificationCode() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(UserService.class).getSms(etNumber.getText().toString())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                showCountDown();
            }
        });

    }

    /**
     * 验证码登录接口
     */
    public void smsLogin() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(UserService.class).smsLogin(etNumber.getText().toString().replaceAll(" ", ""), etVerificationNumber.getText().toString(), "device_tokens", "1")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<UserBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<UserBean> t) throws Exception {
                UserLogic.saveUser(t.getData());
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                if (t.getData().isNeed_pwd()) {
                    startActivity(new Intent(LoginActivity.this, SetpasswordActivity.class));
                } else {
                    finish();
                }
            }
        });
    }


    /**
     * 判断手机号合法才显示获取验证码可点击
     * 验证码和手机号码合法才能登陆
     */

    public boolean checkContent(boolean isCheckCode) {
        setIconIndicator();
        if (StringUtils.isEmpty(etNumber.getText().toString().replaceAll(" ", "")) || etNumber.getText().toString().length() < 11) {
            return false;
        }
        if (isCheckCode) {
            if (StringUtils.isEmpty(etVerificationNumber.getText().toString().replaceAll(" ", "")) || etVerificationNumber.getText().toString().length() < 4) {
                return false;
            }
        }
        return true;
    }

    /**
     * 删除icon显示与隐藏
     */
    public void setIconIndicator() {
        if (etNumber.length() > 0) {
            ivIcon.setVisibility(View.VISIBLE);
        } else {
            ivIcon.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.tv_text)
    public void onTvTextClicked() {
        isCodeLogin = !isCodeLogin;
        initView();
    }


    @OnClick(R.id.tv_obtain_verification)
    public void onTvObtainVerificationClicked() {
        if (checkContent(false)) {
            getVerificationCode();
        }
    }

    @OnClick(R.id.tv_login)
    public void onTvLoginClicked() {
        if (checkContent(true)) {
            if (isCodeLogin){
                smsLogin();
            }else {
                accountPassword();
            }
        }
    }

    /**
     * 账户密码登录接口
     */
    public void accountPassword() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(UserService.class).userLogin(etNumber.getText().toString(), etVerificationNumber.getText().toString(), "device_tokens", "1")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<UserBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<UserBean> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                UserLogic.saveUser(t.getData());
                finish();
            }
        });
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

