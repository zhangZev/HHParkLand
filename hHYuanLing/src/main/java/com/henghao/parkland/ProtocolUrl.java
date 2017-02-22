/*
 * 文件名：ProtocolUrl.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015-4-20
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProtocolUrl {

    /**
     * 服务端根地址
     */
    public static String ROOT_URL = "";

    public static boolean isURL = true;

    static {
        if (!isURL) {
            // 生产地址
            ROOT_URL = "http://safe.higdata.com/YL_BigData";
        } else {
            // 测试地址/192.168.1.12
            ROOT_URL = "http://172.16.13.101:8080/YL_BigData";
            //172.16.13.101:8080/login/az?username=?&password=?
            /*ROOT_URL = "http://safe.higdata.com/Java_Nfc/";*/
//			ROOT_URL = "http://192.168.1.12/Java_Nfc";
        }
    }

    // TODO 用户相关
    /************************
     * 用户相关
     **************************/
    public static final String USER = "user/";

    /**
     * 用户登录172.16.13.101:8080/login/az?username=?&password=?
     */
    public static String APP_LOGIN = "login/az";

    public static String APP_GET_NFCBYID = "login/az";
    /**
     * 用户注册
     */
    public static String APP_REG = "register";

    /************************ 用户相关 end **************************/

    // TODO app系统 相关
    /************************
     * app系统 相关
     **************************/
    public static final String SYSTEM = "j_appSystem/";

    /**
     * app启动页面信息
     */
    public static final String APP_START = SYSTEM + "appStart";

    /**
     * app引导页面信息
     */
    public static final String APP_GUIDE = SYSTEM + "appGuide";

    /**
     * app系统版本更新
     */
    public static final String APP_SYS_UPDATE = SYSTEM + "appUserUpdate";
    /************************ app系统 end **************************/
    /************************
     * 签到
     **************************/

    public static final String APP_QIANDAO = "report";

    /************************ 签到 end **************************/
    /************************
     * 养护管理
     **************************/
//   http://172.16.13.101:8080/YL_BigData/queryGhManagemsg?yid&treeId

    public static final String APP_YANGHU = "queryGhManagemsg";

    /**
     * 查询单个
     */
    public static final String APP_YANGHUQUERY = "queryGhmsgByid";

    /************************ 养护管理 end **************************/
    /************************
     * 项目管理
     **************************/

    /**
     * 日志备忘
     */
    public static final String PROJECT_RZBEIWANG = "http://172.16.13.101:8080/YL_BigData/saveJournalMemoMsg";

    /**
     * 施工钱包查询
     */
    public static final String PROJECT_SGWALLET = "queryWalletMsg";

    /**
     * 施工备忘提交
     */
    public static final String PROJECT_SGBEIWANG = "saveSgmemoMsg";

    /**
     * 工作备忘提交
     */
    public static final String PROJECT_WORKBEIWANG = "saveWorkMemoMsg";

    /**
     * 施工资料提交
     */
    public static final String PROJECT_SGINFO = "http://172.16.13.101:8080/YL_BigData/" + "saveBiuldDataMsg";

    /************************ 项目管理 end **************************/

    /**
     * 上传错误日志到服务器
     */
    public static final String UPLOAD_ERROR_SERVER = "appError";


}
