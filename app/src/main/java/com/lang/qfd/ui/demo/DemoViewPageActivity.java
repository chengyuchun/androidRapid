package com.lang.qfd.ui.demo;

import android.os.Bundle;

import com.framework.delegate.ViewPageDelegate;
import com.lang.core.BaseActivityPresenter;

/**
 * Created by chengyuchun on 2017/9/12.
 *
 * viewpageActivity的写法，使用ViewPageDelegate
 * 注意FragmentManager使用getSupportFragmentManager
 */

public class DemoViewPageActivity extends BaseActivityPresenter<ViewPageDelegate> {
    @Override
    protected Class<ViewPageDelegate> getDelegateClass() {
        return ViewPageDelegate.class;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDelegate.setFragmentManager(getSupportFragmentManager());
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setTitle("girls");
        viewDelegate.addViewPageTab("Demo1",new DemoListFragment());
        viewDelegate.addViewPageTab("Demo2",new DemoListFragment());
        viewDelegate.addViewPageTab("Demo3",new DemoListFragment());
    }
}
