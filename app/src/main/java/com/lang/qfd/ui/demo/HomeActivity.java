package com.lang.qfd.ui.demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.lang.R;
import com.lang.constants.AppConstants;
import com.lang.core.BaseActivityPresenter;
import com.lang.core.ITabFragment;
import com.lang.qfd.ui.invest.fragment.TabInvestFragment;
import com.lang.qfd.ui.main.fragment.TabMainFragment;
import com.lang.widgets.tab.TabLayout;

import java.util.ArrayList;

import static com.lang.constants.AppConstants.INDEX;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarBlack();
        }
        ArrayList<TabLayout.Tab> tabs = new ArrayList<>();
        tabs.add(new TabLayout.Tab(R.drawable.selector_tab_contact, R.string.home_tab_main, TabMainFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.selector_tab_moments, R.string.home_tab_invest, TabInvestFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.selector_tab_profile, R.string.home_tab_profile, DemoListFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.selector_tab_profile, R.string.home_tab_profile, DemoViewPageFragment.class));
        viewDelegate.setTabs(tabs,this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarBlack(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onTabClick(TabLayout.Tab tab) {
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

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int index = intent.getIntExtra(INDEX, AppConstants.TAB_HOME);
        viewDelegate.setCurrentPage(index);
    }

    public static void goBackToMain(Context c) {
        goBackToMain(c, AppConstants.TAB_HOME, true);
    }

    public static void goBackToMain(Context c, int pageCode) {
        goBackToMain(c, pageCode, false);
    }

    public static void goBackToMain(Context c, int pageCode, boolean logout) {
        if (logout) {

        }
        Intent intent = new Intent(c, HomeActivity.class);
        intent.putExtra(INDEX, pageCode);
        c.startActivity(intent);
    }
}
