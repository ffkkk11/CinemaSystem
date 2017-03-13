package com.mju.util;

/**
 * Created by Hua on 2017/3/2.
 */
public abstract class Const {
    public static final int PAGE = 1;//默认页号
    public static final int PRE_PAGE = 10;//默认页大小
    public static final int SUCCESS = 1;
    public static final int FAILED = -1;

    public static final int ORDER_STATUS_W = 1;//待付款
    public static final int ORDER_STATUS_Y = 2;// 已付款
    public static final int ORDER_STATUS_C = 3;//已关闭
    public static final int ORDER_STATUS_E = 4;//错误

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";


}
