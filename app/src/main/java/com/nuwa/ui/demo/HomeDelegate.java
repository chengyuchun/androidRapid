package com.nuwa.ui.demo;

import com.kymjs.frame.view.AppDelegate;
import com.nuwa.R;
import com.nuwa.widgets.tab.TabLayout;

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

    public void setCurrentPage(int pageCode){
        TabLayout tabLayout = get(R.id.mTabLayout);
        tabLayout.setCurrentTab(pageCode);
    }
}
