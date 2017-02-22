/* 
 * 文件名：BasePageModel.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年5月10日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PageModel<T> {

    @Expose
    private List<T> results = new ArrayList<T>(0);// 显示的数据集合

    @Expose
    private int currPage;// 当前页

    @Expose
    private int rows;// 当前行

    @Expose
    private int allRows;// 总行,数据库来的.

    @Expose
    private int allPages;// 总页数

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getAllRows() {
        return allRows;
    }

    public void setAllRows(int allRows) {
        this.allRows = allRows;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }
}
