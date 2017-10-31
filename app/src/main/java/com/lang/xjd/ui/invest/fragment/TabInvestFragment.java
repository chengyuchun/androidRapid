package com.lang.xjd.ui.invest.fragment;

import android.support.v4.app.Fragment;

import com.framework.fragment.BaseFragmentPresenter;
import com.lang.core.ITabFragment;
import com.lang.xjd.ui.invest.adapter.ContactBean;
import com.lang.xjd.ui.invest.delegate.TabInvestDelegate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chengyuchun on 2016/9/7.
 */
public class TabInvestFragment extends BaseFragmentPresenter<TabInvestDelegate> implements ITabFragment {
    @Override
    protected Class getDelegateClass() {
        return TabInvestDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.notifyListDataChange(getContacts());
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

    private List<ContactBean> getContacts(){
//        List<ContactBean> list = new ArrayList<ContactBean>();

        ContactBean[] contactArray = { // Comment to prevent re-format
                new ContactBean("活期产品", "活期"),

                new ContactBean("短期产品A", "短期"),
                new ContactBean("短期产品B", "短期"),
                new ContactBean("短期产品C", "短期"),
                new ContactBean("短期产品D", "短期"),

                new ContactBean("中长期产品A", "中长期"),
                new ContactBean("中长期产品B", "中长期"),
                new ContactBean("中长期产品C", "中长期"),
                new ContactBean("中长期产品D", "中长期"),
                new ContactBean("中长期产品E", "中长期"),
                new ContactBean("中长期产品F", "中长期"),
        };
        System.out.println("show...");
        return Arrays.asList(contactArray);
    }

}
