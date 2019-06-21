package com.ifhu.meiwei.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ifhu.meiwei.bean.ShoppingCartBean;

import static com.ifhu.meiwei.utils.Constants.LOCALSHOPCART;

/**
 * 保存本地购物车基本对象
 * @author KevinFu
 */
public class LocalShopHelper {

    public static void saveShoppingCartBean(ShoppingCartBean shoppingCartBean,String storeId) {
        if (shoppingCartBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(shoppingCartBean);
            SharedPreUtil.getInstance().saveString(LOCALSHOPCART+storeId, json);
        }
    }


    public static ShoppingCartBean getShoppingCartBean(String storeId) {
        String json = SharedPreUtil.getInstance().getString(LOCALSHOPCART+storeId, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            ShoppingCartBean shoppingCartBean = gson.fromJson(json, ShoppingCartBean.class);
            return shoppingCartBean;
        }
        return null;
    }


    public static boolean isShoppingCartEmpty(String storeId){
        ShoppingCartBean shoppingCartBean = getShoppingCartBean(storeId);
        if (shoppingCartBean == null || shoppingCartBean.getList().size() == 0){
            return true;
        }
        return false;
    }

    public static void clearLoaclShopCart(String storeId) {
        SharedPreUtil.getInstance().clearSingle(LOCALSHOPCART+storeId);
    }
}
