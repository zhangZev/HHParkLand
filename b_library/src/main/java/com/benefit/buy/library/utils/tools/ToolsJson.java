/* 
 * 文件名：ToolsJson.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.benefit.buy.library.utils.tools;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-22
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ToolsJson {

    private static GsonBuilder gsonb = new GsonBuilder();

    public static <T> T parseObjecta(String jsonData, Type fooType) throws JsonSyntaxException {
        gsonb.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonb.create();
        return gson.fromJson(jsonData, fooType);
    }

    public static <T> T parseObjecta(String jsonData, Class<T> fooType) throws JsonSyntaxException {
        gsonb.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonb.create();
        return gson.fromJson(jsonData, fooType);
    }

    public static String toJson(Object obj) {
        gsonb.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonb.serializeNulls().create();
        return gson.toJson(obj);
    }
}
