package com.henghao.parkland.model.protocol;

/**
 * Created by 晏琦云 on 2017/2/13.
 * 公共网络接口类
 */

public class HttpPublic {
    public static final String SAVETREE = "http://172.16.13.101:8080/YL_BigData/saveTree/az";//录入植物信息接口
    public static final String QUERYBYID = "http://172.16.13.101:8080/YL_BigData/queryById/az";//查询二维码信息接口
    public static final String SAVESTATUS = "http://172.16.13.101:8080/YL_BigData/saveStatus/az";//保存养护状态接口
    public static final String YHMSG = "http://172.16.13.101:8080/YL_BigData/yhmsg/az";//查询养护管理信息接口
    public static final String SAVEGHMSG = "http://172.16.13.101:8080/YL_BigData/saveghmsg/az";//保存管护信息接口
    public static final String QIANDAO = "http://172.16.13.101:8080/YL_BigData/report";//签到
}
