package com.framework.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framework.R;
import com.framework.delegate.BaseListDelegate;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by chengyuchun on 2017/3/7.
 */

public abstract class BaseListFragment<T extends BaseListDelegate> extends BaseFragmentPresenter<BaseListDelegate> {
    protected XRecyclerView mRecyclerView;
    protected MultiItemTypeAdapter mAdapter;
    @Override
    protected Class<BaseListDelegate> getDelegateClass() {
        return BaseListDelegate.class;
    }

    @Override
    protected void bindEvenListener() {

        mRecyclerView = viewDelegate.get(R.id.MyRecyclerView);
        loadData();
        mAdapter = getAdapter();
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                BaseListFragment.this.onRefresh();
                mRecyclerView.refreshComplete();

            }

            @Override
            public void onLoadMore() {
                // load more data here
                BaseListFragment.this.onLoadMore();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.loadMoreComplete();
                        //mRecyclerView.scrollToPosition(mAdapter.getDatas().size()-1);
                    }
                },1500);
            }
        });


    }

    protected RecyclerView.LayoutManager getLayoutManager(){
        //默认是LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return  layoutManager;
    }

    public <P> void refreshData(List<P> datas) {
        if (mAdapter.getDatas() != null && !mAdapter.getDatas().isEmpty()) {
            //去重
            for (P data : datas) {
                if (!mAdapter.getDatas().contains(data))
                    mAdapter.getDatas().add(data);
            }
        } else {
            if (datas == null) {
                return;
            } else {
                mAdapter.getDatas().addAll(datas) ;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    protected  abstract void loadData();

    protected  abstract MultiItemTypeAdapter getAdapter();

    protected abstract void onRefresh();

    protected abstract void onLoadMore();
}
