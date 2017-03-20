package com.lang.qfd.ui.main.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kymjs.frame.view.AppDelegate;
import com.lang.R;
import com.lang.qfd.model.Benefit;
import com.lang.utils.GlideUtil;
import com.lang.utils.L;
import com.lang.widgets.CustomScrollView;
import com.lang.widgets.ScrollViewListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * Created by chengyuchun on 2017/3/14.
 */

public class TabMainDelegate extends AppDelegate implements ScrollViewListener {
    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_tab_main;
    }

    @Override
    public void initWidget() {
        CustomScrollView customScrollView = get(R.id.mScrollView);
        customScrollView.setScrollViewListener(this);
        getToolbar().setAlpha(0);
        initToolbar();
    }

    public void refreshRecyclerViewData(List<Benefit> list){
        RecyclerView recyclerView = get(R.id.mRecyclerView);
        CommonAdapter commonAdapter = (CommonAdapter)recyclerView.getAdapter();
        if(commonAdapter == null) {
            commonAdapter = new CommonAdapter<Benefit>(getActivity(), R.layout.orientation_list_item, list) {
                @Override
                protected void convert(ViewHolder holder, Benefit benefit, int position) {
                    holder.setText(R.id.mSampleListItemLabel, position + "");
                    GlideUtil.displayImg((ImageView) holder.getView(R.id.mSampleListItemImg), benefit.url);
                }
            };
            recyclerView.setAdapter(commonAdapter);
        }else {
            commonAdapter.getDatas().clear();
            commonAdapter.getDatas().addAll(list);
            commonAdapter.notifyDataSetChanged();
        }
    }

    public void setRecyOrientation(){
        RecyclerView recyclerView = get(R.id.mRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public Toolbar getToolbar() {
        return get(R.id.toolbar);
    }

    private void initToolbar(){
        ((TextView)get(R.id.toolbar_left)).setText("注册");
        ((TextView)get(R.id.toolbar_right)).setText("登录");
    }

    @Override
    public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {

        LinearLayout lyRegister = get(R.id.ly_register);
        int height = lyRegister.getMeasuredHeight()-getToolbar().getMeasuredHeight();
        if(height > 0 && height>=y){
            float alpha = (float)y/(float)height;
            getToolbar().setAlpha(alpha);
            L.d("alpha:"+alpha);
            // mToolbar.setAlpha((int) (alpha * 255));
            // 通知标题栏刷新显示
            getToolbar().invalidate();
        }else {
            getToolbar().setAlpha(1f);
            getToolbar().invalidate();
        }
    }
}
