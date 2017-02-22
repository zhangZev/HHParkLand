/* 
 * 文件名：ChannelProgramException.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.exception;

import android.content.Context;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qinyulun
 * @version HDMNV100R001, 2014-12-30
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ChannelProgramException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Context mContext;

    public ChannelProgramException(Context cxt) {
        super();
        init(cxt);
    }

    public ChannelProgramException(Context cxt, String message, Throwable cause) {
        super(message, cause);
        init(cxt);
    }

    public ChannelProgramException(Context cxt, String tag, String message) {
        super(message);
        init(cxt);
    }

    public ChannelProgramException(Context cxt, Throwable cause) {
        super(cause);
        init(cxt);
    }

    private void init(Context cxt) {
        mContext = cxt;
    }
}
