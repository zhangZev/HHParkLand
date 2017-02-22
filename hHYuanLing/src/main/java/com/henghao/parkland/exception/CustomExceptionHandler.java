/* 
 * 文件名：CustomExceptionHandler.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

import org.json.JSONException;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.NSLog;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.ascyn.BusinessResponse;
import com.henghao.parkland.model.protocol.UploadProtocol;

import android.content.Context;

/**
 * app异常处理
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-17
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CustomExceptionHandler implements UncaughtExceptionHandler, BusinessResponse {

    private UncaughtExceptionHandler defaultUEH;

    private String localPath;

    private static CustomExceptionHandler mExceptionHandler;

    private UploadProtocol mUploadProtocol;

    private CustomExceptionHandler() {
    }

    public CustomExceptionHandler(String localPath) {
        this.localPath = localPath;
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static CustomExceptionHandler getInstance() {
        if (mExceptionHandler == null) {
            mExceptionHandler = new CustomExceptionHandler();
        }
        return mExceptionHandler;
    }

    /**
     * @param localPath 本地存存储日志的路径
     * @param url 服务端存储路径
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void init(Context ctx, String localPath) {
        this.localPath = localPath;
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mUploadProtocol = new UploadProtocol(ctx);
        mUploadProtocol.addResponseListener(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();
        String filename = currentTimeStamp + ".txt";
        NSLog.e(this, stacktrace);
        if (localPath != null) {
            writeToFile(stacktrace, filename);
        }
        sendToServer(e.getMessage(), filename);
        defaultUEH.uncaughtException(thread, e);
    }

    /**
     * 将错误信息写入本地
     * @param stacktrace
     * @param filename
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void writeToFile(String stacktrace, String filename) {
        try {
            BufferedWriter bos = new BufferedWriter(new FileWriter(localPath + "/" + filename));
            bos.write(stacktrace);
            bos.flush();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将错误信息发送到服务端
     * @param stacktrace
     * @param filename
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void sendToServer(String stacktrace, String filename) {
        mUploadProtocol.uploadWithErrorServer(stacktrace);
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ProtocolUrl.UPLOAD_ERROR_SERVER)) {
        }
    }
}
