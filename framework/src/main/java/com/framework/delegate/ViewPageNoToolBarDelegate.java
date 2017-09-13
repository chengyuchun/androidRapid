package com.framework.delegate;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.framework.R;


/**
 * Created by chengyuchun on 2017/3/16.
 *
 * 有viewpage但没有toolbar的页面使用该类
 */

public class ViewPageNoToolBarDelegate extends ViewPageDelegate {

    @Override
    public void initWidget() {
        super.initWidget();
        getToolbar().setVisibility(View.GONE);
    }


    public void removeAllViews(){
        TabLayout tabLayout = get(R.id.tab_layout);
        tabLayout.removeAllViews();
        ViewPager viewPager = get(R.id.tab_viewPager);
        viewPager.removeAllViews();
    }

}
