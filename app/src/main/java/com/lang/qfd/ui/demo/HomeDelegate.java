package com.lang.qfd.ui.demo;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kymjs.frame.view.AppDelegate;
import com.lang.R;
import com.lang.widgets.tab.TabLayout;

import java.util.ArrayList;

/**
 * Created by chengyuchun on 2017/3/7.
 */

public class HomeDelegate extends AppDelegate  {

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_tab;
    }

    public void setTabs(ArrayList arrayList,TabLayout.OnTabClickListener onTabClickListener){
        TabLayout tabLayout = get(R.id.mTabLayout);
        tabLayout.setUpData(arrayList, onTabClickListener);
        tabLayout.setCurrentTab(0);
    }

    public void setTitle(String s){
        Toolbar toolbar = get(R.id.toolbar);
        TextView titleView = get(R.id.toolbar_title);
        titleView.setText(s);
    }

    public void showToolBar(){
        get(R.id.toolbar).setVisibility(View.VISIBLE);
    }

    public void hiddenToolBar(){
        get(R.id.toolbar).setVisibility(View.GONE);
    }

}
