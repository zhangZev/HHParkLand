/**
 * 
 */
package com.henghao.parkland;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.benefit.buy.library.utils.tools.ToolsFile;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.exception.CustomExceptionHandler;
import com.henghao.parkland.service.ReConnectService;
import com.henghao.parkland.utils.LocationUtils;

/**
 * @author zhangxianwen
 */
public class FMApplication extends Application {

    /** 对外提供整个应用生命周期的Context **/
    private static Context instance;

    private final List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 对外提供Application Context
     * @return
     */
    public static Context gainContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(this);
        LocationUtils.Location(this);
        appException();
    }

    /**
     * 异常处理类
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void appException() {
        boolean sdcardExist = ToolsFile.isSdcardExist();
        if (!sdcardExist) {
            return;
        }
        String path = Constant.LOG_DIR_PATH;
        ToolsFile.createDirFile(path);
        // catch (Exception e) {
        // //这里不能再向上抛异常，如果想要将log信息保存起来，则抛出runtime异常，
        // 让自定义的handler来捕获，统一将文件保存起来上传
        // throw new RuntimeException(e);
        // }
        CustomExceptionHandler mCustomExceptionHandler = CustomExceptionHandler.getInstance();
        mCustomExceptionHandler.init(getApplicationContext(), path);
    }

    /**
     * 添加Activity到容器中
     * @param activity
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void addActivity(Activity activity) {
        Log.e("@@@", "add:" + activity);
        this.activityList.add(activity);
    }

    /**
     * 删除对应的activity 〈一句话功能简述〉 〈功能详细描述〉
     * @param activity
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void removeActivity(Activity activity) {
        if (!ToolsKit.isEmpty(this.activityList)) {
            Log.e("@@@", "remove:" + activity);
            this.activityList.remove(activity);
        }
    }

    public void stopService() {
        Intent reConnectService = new Intent(this, ReConnectService.class);
        stopService(reConnectService);
    }

    public void startService() {
        // 自动恢复连接服务
        Intent reConnectService = new Intent(this, ReConnectService.class);
        startService(reConnectService);
    }

    /**
     * 遍历所有Activity并finish
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void exit() {
        try {
            for (Activity activity: this.activityList) {
                Log.e("@@@", "exit:" + activity);
                activity.finish();
            }
        }
        catch (Exception e) {
            System.exit(1);
        }
    }
}
