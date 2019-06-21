package com.ifhu.meiwei.utils;

import com.ifhu.meiwei.MyApplication;

/**
 * 静态字符串
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public class Constants {
    /**
     * 文件名称
     */
    public static final String APP_PREFERENCE = "APP_PREFERENCE_MEI_WEI";

    /**
     * 保存用户对象
     */
    public static final String USER = "mei_wei_user";

    /**
     * 保存本地购物车对象
     */
    public static final String LOCALSHOPCART = "shopCartLocal_";

    /**
     * 退出登录
     */
    public static final String LOGOUT = "logout_event";

    /**
     * 页面间数据传递字段
     */
    public static String DATA = "DATA";

    public static final String LOCATION_DATAUPDATA = "location";
    public static final String LOCATION_DATAUPDATAFAIL = "LOCATION_DATAUPDATAFAIL";
    public static final String GOTOHOMEPAGE = "GOTOHOMEPAGE";

    /**
     * 重新定位事件
     */
    public static final String RELOCATION = "RELOCATION";

    public static final String ORDER_DATAUPDATA = "ORDER_DATAUPDATA";


    public static final String MONEYUNIT = "￥";

    public  static String FileProviderName = ProviderUtil.getFileProviderName(MyApplication.getApplication());
    public  static String APP_ID = "wxbcdccb2c1d95e94b";


    public static final String PAYSUCCESSWITHWXPAY = "PAYSUCCESSWITHWXPAY";

    public static final String CHOOSECITY = "CHOOSECITY";

    public static final String MERPHONENUMBER = "MERPHONENUMBER";

}
