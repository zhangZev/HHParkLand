package com.henghao.parkland.model.ascyn;

import java.util.Map;

import com.benefit.buy.library.http.query.AQuery;
import com.benefit.buy.library.http.query.callback.AjaxCallback;
import com.henghao.parkland.ProtocolUrl;

import android.content.Context;

public class BeeQuery<T> extends AQuery {

    public BeeQuery(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public static final int ENVIRONMENT_PRODUCTION = 1;

    public static final int ENVIROMENT_DEVELOPMENT = 2;

    public static final int ENVIROMENT_MOCKSERVER = 3;

    public static int environment() {
        return ENVIRONMENT_PRODUCTION;
    }

    public static String serviceUrl() {
        if (ENVIRONMENT_PRODUCTION == BeeQuery.environment()) {
            return ProtocolUrl.ROOT_URL + "/";
        }
        else {
            return ProtocolUrl.ROOT_URL + "/";
        }
    }

    @Override
    public <K> AQuery ajax(AjaxCallback<K> callback) {
        if (BeeQuery.environment() == BeeQuery.ENVIROMENT_MOCKSERVER) {
            MockServer.ajax(callback);
            return null;
        }
        else {
            String url = callback.getUrl();
            String absoluteUrl = getAbsoluteUrl(url);
            callback.url(absoluteUrl);
        }
        if (BeeQuery.environment() == BeeQuery.ENVIROMENT_DEVELOPMENT) {
            DebugMessageModel.addMessage((BeeCallback) callback);
        }
        return super.ajax(callback);
    }

    public <K> AQuery ajaxAbsolute(AjaxCallback<K> callback) {
        return super.ajax(callback);
    }

    public <K> AQuery ajax(String url, Map<String, ?> params, Class<K> type, BeeCallback<K> callback) {
        callback.type(type).url(url).params(params);
        if (BeeQuery.environment() == BeeQuery.ENVIROMENT_MOCKSERVER) {
            MockServer.ajax(callback);
            return null;
        }
        else {
            String absoluteUrl = getAbsoluteUrl(url);
            callback.url(absoluteUrl);
        }
        return ajax(callback);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BeeQuery.serviceUrl() + relativeUrl;
    }
}
