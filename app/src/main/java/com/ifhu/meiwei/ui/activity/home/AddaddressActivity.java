package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.UserService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.StringUtils;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增地址页面
 */
public class AddaddressActivity extends BaseActivity {
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_house_number)
    EditText etHouseNumber;
    @BindView(R.id.tv_ok)
    TextView tvOk;

    @BindView(R.id.iv_man)
    ImageView ivMan;
    @BindView(R.id.iv_woman)
    ImageView ivWoman;

    int address_id;
    int sex = 1;
    int is_default;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        tvReturn.setVisibility(View.INVISIBLE);
        tvText.setVisibility(View.INVISIBLE);
        tvTitle.setText("新增地址");
        ivMan.setSelected(true);

    }


    /**
     * 判断是否为空
     */
    public boolean checkair() {

        if (StringUtils.isEmpty(etName.getText().toString())) {
            ToastHelper.makeText("请输入收货人姓名").show();
            return false;
        }
        if (StringUtils.isEmpty(etName.getText().toString())) {
            ToastHelper.makeText("请输入手机号码").show();
            return false;
        }
        if (StringUtils.isEmpty(etAddress.getText().toString())) {
            ToastHelper.makeText("请选择地址").show();
            return false;
        }
        if (StringUtils.isEmpty(etHouseNumber.getText().toString())) {
            ToastHelper.makeText("请输入你的详细地址").show();
            return false;
        }
        return true;
    }

    public void setaddress() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(UserService.class).userAddressSave(address_id, UserLogic.getUser().getMember_id() + "",
                etName.getText().toString(),
                sex,
                etPhoneNumber.getText().toString(),
                etAddress.getText().toString(),
                etHouseNumber.getText().toString(),
                is_default).compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                finish();
            }

        });

    }


    @OnClick(R.id.rl_return)
    public void onRlReturnClicked() {
        finish();
    }

    @OnClick(R.id.tv_ok)
    public void onTvOkClicked() {
        if(checkair()) {
            setaddress();
        }
    }

    @OnClick(R.id.iv_man)
    public void onIvManClicked() {
        selectedGender(true);
    }

    @OnClick(R.id.iv_woman)
    public void onIvWomanClicked() {
        selectedGender(false);
    }

    /**
     * 判断男女性别
     *
     * @param man
     */
    public void selectedGender(boolean man) {
        ivMan.setSelected(man);
        ivWoman.setSelected(!man);
        sex = man ? 1 : 2;
    }
}
