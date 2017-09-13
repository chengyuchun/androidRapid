package com.framework.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * author zhetengxiang
 * Date 2016/7/27
 */

public class PtrlMeiTuanFrameLayout extends PtrFrameLayout {
    public PtrlMeiTuanFrameLayout(Context context) {
        super(context);
        init();
    }

    public PtrlMeiTuanFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PtrlMeiTuanFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        PtrMeiTuanHeader mHeaderView = new PtrMeiTuanHeader(getContext());
        setHeaderView(mHeaderView);
        addPtrUIHandler(mHeaderView);
    }
}
