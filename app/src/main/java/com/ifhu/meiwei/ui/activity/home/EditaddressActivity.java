package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.EditadreessBean;
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
 * 编辑地址页面
 */
public class EditaddressActivity extends BaseActivity {
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_man)
    ImageView ivMan;
    @BindView(R.id.iv_woman)
    ImageView ivWoman;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_house_number)
    EditText etHouseNumber;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    int sex = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        tvReturn.setVisibility(View.INVISIBLE);
        tvTitle.setText("编辑地址");
        tvText.setText("删除");
        editAddress();
    }


    /**
     * 判断是否为空
     */
    public boolean checkair() {
        if (StringUtils.isEmpty(etName.getText().toString())) {
            ToastHelper.makeText("请输入收货人姓名").show();
            return false;
        }
        if (StringUtils.isEmpty(etPhoneNumber.getText().toString())) {
            ToastHelper.makeText("请输入手机号").show();
            return false;
        }
        if (StringUtils.isEmpty(tvAddress.getText().toString())) {
            ToastHelper.makeText("请输入地址").show();
            return false;
        }
        if (StringUtils.isEmpty(etHouseNumber.getText().toString())) {
            ToastHelper.makeText("请输入门牌号").show();
            return false;
        }
        return true;
    }

    /**
     * 编辑地址接口
     */

    public void editAddress() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(UserService.class).userAddressInfo(getDataInt())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<EditadreessBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<EditadreessBean> t) throws Exception {
                initView(t.getData());
            }
        });
    }

    public void initView(EditadreessBean editadreessBean) {
        etName.setText(editadreessBean.getTrue_name());
        etPhoneNumber.setText(editadreessBean.getMob_phone());
        tvAddress.setText(editadreessBean.getArea_info());
        etHouseNumber.setText(editadreessBean.getAddress());
        selectedGender(editadreessBean.getSex() == 1);
    }


    @OnClick(R.id.rl_return)
    public void onRlReturnClicked() {
        finish();
    }

    @OnClick(R.id.tv_ok)
    public void onTvOkClicked() {
        if (checkair()) {
            setaddress();
        }
    }

    /**
     * 新增地址
     */
    public void setaddress() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(UserService.class).userAddressSave(getDataInt(), UserLogic.getUser().getMember_id() + "",
                etName.getText().toString(), sex, etPhoneNumber.getText().toString(), tvAddress.getText().toString(),
                etHouseNumber.getText().toString(), 1).compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                checkair();
            }


        });

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

    /**
     * 删除收货地址接口
     */
    public void deleteAddress() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(UserService.class).userAddressDel(UserLogic.getUser().getMember_id(),getDataInt())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                finish();
            }
        });
    }

    @OnClick(R.id.tv_text)
    public void onTvTextClicked() {
        deleteAddress();
    }
}
