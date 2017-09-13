package com.lang.qfd.ui.demo;

import android.os.Bundle;

import com.framework.delegate.FragmentContainerDelegate;
import com.lang.core.BaseActivityPresenter;


/**
 * Created by chengyuchun on 2017/3/7.
 *
 * 一个activity只包含一个Fragment的写法,即使用FragmentContainerDelegate
 */
public class DemoContainerActivity extends BaseActivityPresenter<FragmentContainerDelegate> {
    @Override
    protected Class<FragmentContainerDelegate> getDelegateClass() {
        return FragmentContainerDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDelegate.setFragment(new DemoListFragment());
    }
}