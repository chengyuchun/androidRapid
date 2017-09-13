package com.framework.delegate;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.framework.R;
import com.framework.fragment.BaseFragmentPresenter;
import java.util.ArrayList;

/**
 * Created by chengyuchun on 2017/3/16.
 *
 * 有viewpage的页面使用该类
 */

public class ViewPageDelegate extends ToolbarDelegate {
    private FragmentManager mFragmentManager;
    private ArrayList<String> mTitleList = new ArrayList<String>();
    private ArrayList<BaseFragmentPresenter> arrayListFragment = new ArrayList<BaseFragmentPresenter>();
    private FragmentStatePagerAdapter mFragmentStatePagerAdapter;
    public TabLayout tabLayout;
    @Override
    public int getRootLayoutId() {
        return R.layout.widget_view_page;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initViewPageTab();
    }



    public void addViewPageTab(String title, BaseFragmentPresenter fragment){
        mTitleList.add(title);
        arrayListFragment.add(fragment);
        mFragmentStatePagerAdapter.notifyDataSetChanged();
    }

    public void setFragmentManager(FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

    private void initViewPageTab(){
        tabLayout = get(R.id.tab_layout);
        final ViewPager viewPager = get(R.id.tab_viewPager);

        tabLayout.setupWithViewPager(viewPager);

        mFragmentStatePagerAdapter = new FragmentStatePagerAdapter(mFragmentManager){
            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.size();
            }

            @Override
            public Fragment getItem(int index)//直接创建fragment对象并返回
            {
                return arrayListFragment.get(index);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return mTitleList == null ? super.getPageTitle(position) : mTitleList.get(position);
            }
        };

        viewPager.setAdapter(mFragmentStatePagerAdapter);
    }

}
