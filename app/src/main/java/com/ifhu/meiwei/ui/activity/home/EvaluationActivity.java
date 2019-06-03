package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.EvaluationBean;
import com.ifhu.meiwei.bean.OrderinfoBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.Constants;
import com.ifhu.meiwei.utils.UserLogic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ifhu.meiwei.utils.Utils.getContext;

/**
 * 评价页面
 */
public class EvaluationActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_rider_name)
    TextView tvRiderName;
    @BindView(R.id.tv_rider_time)
    TextView tvRiderTime;
    @BindView(R.id.tv_rider_suggest)
    TextView tvRiderSuggest;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_store_suggest)
    TextView tvStoreSuggest;
    @BindView(R.id.tv_add_phone)
    TextView tvAddPhone;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.rl_nosatisfaction)
    RelativeLayout rlNosatisfaction;
    @BindView(R.id.rl_satisfaction)
    RelativeLayout rlSatisfaction;
    @BindView(R.id.ll_comprehensive)
    LinearLayout llComprehensive;
    @BindView(R.id.ll_taste)
    LinearLayout llTaste;
    @BindView(R.id.ll_package)
    LinearLayout llPackage;
    @BindView(R.id.iv_rider_photo)
    GlideImageView ivRiderPhoto;
    @BindView(R.id.iv_store_photo)
    GlideImageView ivStorePhoto;
    @BindView(R.id.lv_commodity)
    ListView lvCommodity;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.iv_nolike)
    ImageView ivNolike;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        ButterKnife.bind(this);
        tvReturn.setVisibility(View.INVISIBLE);
        tvTitle.setText("评价");
        tvText.setVisibility(View.INVISIBLE);
    }

    /**
     * 获取评价信息接口
     */
    public void storeCom() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(HomeService.class).storeCom(getDATA(), UserLogic.getUser().getMember_id() + "")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<EvaluationBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<EvaluationBean> t) throws Exception {
                initView(t.getData());
            }
        });
    }

    public void initView(EvaluationBean evaluationBean) {
        ivRiderPhoto.load(evaluationBean.getQishou().getAvator());
        tvRiderName.setText(evaluationBean.getQishou().getMember_name());
        tvRiderTime.setText(evaluationBean.getQishou().getTime());
        tvStoreName.setText(evaluationBean.getInfo().getStore_name());
        ivRiderPhoto.load(evaluationBean.getInfo().getStore_avatar());
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_ok)
    public void onTvOkClicked() {
    }

    public void selectedSatisfaction(boolean Satisfaction) {
        rlSatisfaction.setSelected(Satisfaction);
        rlNosatisfaction.setSelected(!Satisfaction);

    }

    @OnClick(R.id.rl_nosatisfaction)
    public void onRlNosatisfactionClicked() {
        selectedSatisfaction(false);
    }

    @OnClick(R.id.rl_satisfaction)
    public void onRlSatisfactionClicked() {
        selectedSatisfaction(true);
    }

}
