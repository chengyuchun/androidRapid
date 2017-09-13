package com.framework.delegate;

/**
 * Created by chengyuchun on 2017/4/17.
 *
 * 有返回箭头（功能）的页面继承该类
 */

public abstract class BackToolbarDelegate extends ToolbarDelegate{
    @Override
    public void initWidget() {
        super.initWidget();
        setBackHead();
    }
}
