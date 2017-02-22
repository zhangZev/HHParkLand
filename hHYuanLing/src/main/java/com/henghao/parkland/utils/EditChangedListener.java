/* 
 * 文件名：EditChangedListener.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.utils;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 输入框限制〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年6月6日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EditChangedListener implements TextWatcher {

    private CharSequence temp;//监听前的文本

    private int editStart;//光标开始位置

    private int editEnd;//光标结束位置

    private int charMaxNum = 18;

    private int charMinNum = 0;

    EditText mEditText;

    public EditChangedListener(EditText editText) {
        mEditText = editText;
    }

    public void setCharMaxNum(int charMaxNum) {
        this.charMaxNum = charMaxNum;
    }

    public void setCharMinNum(int charMinNum) {
        this.charMinNum = charMinNum;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        temp = s;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
        editStart = mEditText.getSelectionStart();
        editEnd = mEditText.getSelectionEnd();
        if (temp.length() > charMaxNum) {
            mEditText.setError(Html.fromHtml("<font color='red'>输入密码长度" + charMinNum + "至" + charMaxNum + "之间</font>"));
            mEditText.setFocusable(true);
            mEditText.requestFocus();
            s.delete(editStart - 1, editEnd);
            int tempSelection = editStart;
            mEditText.setText(s);
            mEditText.setSelection(tempSelection);
        }
    }
};
