package com.lang.xjd.ui.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.framework.delegate.ViewPageDelegate;
import com.framework.fragment.BaseFragmentPresenter;
import com.lang.core.ITabFragment;

/**
 * Created by chengyuchun on 2017/9/12.
 *
 * viewpageActivity的写法，使用ViewPageDelegate
 * 注意FragmentManager使用 getChildFragmentManager
 */

public class DemoViewPageFragment extends BaseFragmentPresenter<ViewPageDelegate> implements ITabFragment {
    @Override
    protected Class<ViewPageDelegate> getDelegateClass() {
        return ViewPageDelegate.class;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDelegate.setFragmentManager(getChildFragmentManager());
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setTitle("girls");
        viewDelegate.addViewPageTab("Demo1",new DemoListFragment());
        viewDelegate.addViewPageTab("Demo2",new DemoListFragment());
        viewDelegate.addViewPageTab("Demo3",new DemoListFragment());
    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
