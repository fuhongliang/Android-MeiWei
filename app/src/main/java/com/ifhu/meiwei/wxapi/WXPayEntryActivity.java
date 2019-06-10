package com.ifhu.meiwei.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ifhu.meiwei.bean.MessageEvent;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.Constants;
import com.ifhu.meiwei.utils.ToastHelper;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import static com.ifhu.meiwei.utils.Constants.LOCATION_DATAUPDATA;
import static com.ifhu.meiwei.utils.Constants.PAYSUCCESSWITHWXPAY;

/**
 * @author fuhongliang
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
//		if (!EventBus.getDefault().isRegistered(this)) {
//			EventBus.getDefault().register(this);
//		}
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode){
				case 0:
//					EventBus.getDefault().post(new MessageEvent(PAYSUCCESSWITHWXPAY));
					ToastHelper.makeText("支付成功").show();
					finish();
					break;
				case -1:
					ToastHelper.makeText("未能正常支付-1").show();
					finish();
					break;
				case -2:
					ToastHelper.makeText("用户取消支付").show();
					finish();
					break;
					default:
						break;
			}
		}
	}

//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		EventBus.getDefault().unregister(this);
//	}
}