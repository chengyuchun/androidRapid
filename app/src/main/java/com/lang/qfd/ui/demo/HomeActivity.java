package com.lang.qfd.ui.demo;

import android.os.Bundle;

import com.framework.activity.BaseActivityPresenter;
import com.lang.R;
import com.lang.core.ITabFragment;
import com.lang.qfd.ui.invest.fragment.TabInvestFragment;
import com.lang.qfd.ui.main.fragment.TabMainFragment;
import com.lang.widgets.tab.TabLayout;

import java.util.ArrayList;

/**
 * Created by chengyuchun on 2017/3/7.
 */
public class HomeActivity extends BaseActivityPresenter<HomeDelegate> implements TabLayout.OnTabClickListener {
    private ITabFragment mTabFragment;
    @Override
    protected Class<HomeDelegate> getDelegateClass() {
        return HomeDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<TabLayout.Tab> tabs = new ArrayList<>();
        tabs.add(new TabLayout.Tab(R.drawable.selector_tab_contact, R.string.home_tab_main, TabMainFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.selector_tab_moments, R.string.home_tab_invest, TabInvestFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.selector_tab_profile, R.string.home_tab_profile, DemoListFragment.class));
        viewDelegate.setTabs(tabs,this);
    }



    @Override
    public void onTabClick(TabLayout.Tab tab) {
        viewDelegate.showToolBar();
        try {
            ITabFragment tmpFragment = (ITabFragment) getSupportFragmentManager().findFragmentByTag(tab.targetFragmentClz.getSimpleName());
            if (tmpFragment == null) {
                tmpFragment = tab.targetFragmentClz.newInstance();
                if (mTabFragment == null) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.mFragmentContainerLayout, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName())
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .hide(mTabFragment.getFragment())
                            .add(R.id.mFragmentContainerLayout, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName())
                            .commit();
                }
            } else {
                getSupportFragmentManager().beginTransaction()
                        .hide(mTabFragment.getFragment())
                        .show(tmpFragment.getFragment())
                        .commit();
            }
            mTabFragment = tmpFragment;
            viewDelegate.setTitle(mTabFragment.getTitle());

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void hiddenToolBar(){
         viewDelegate.hiddenToolBar();
    }
}
