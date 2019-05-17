package com.ifhu.meiwei.utils;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.ifhu.meiwei.bean.UserBean;

import static com.ifhu.meiwei.utils.Constants.USER;

/**
 * 保存用户基本对象
 * @author KevinFu
 */
public class UserLogic {

    public static void saveUser(UserBean dataBean) {
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            SharedPreUtil.getInstance().saveString(USER, json);
        }
    }


    public static UserBean getUser() {
        String json = SharedPreUtil.getInstance().getString(USER, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            UserBean mUser = gson.fromJson(json, UserBean.class);
            return mUser;
        }
        return null;
    }

    public static boolean isLogin() {
        if (getUser() == null || StringUtils.isSpace(getUser().getToken())) {
            return false;
        } else {
            return true;
        }
    }

    public static void loginOut() {
        SharedPreUtil.getInstance().clearSingle(USER);
    }

}
