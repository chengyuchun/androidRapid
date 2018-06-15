package com.nuwa.core;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.framework.delegate.BaseListDelegate;
import com.framework.fragment.BaseFragmentPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nuwa.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by chengyuchun on 2017/3/7.
 */

public abstract class BaseListFragment<T extends BaseListDelegate> extends BaseFragmentPresenter<BaseListDelegate> {
    protected XRecyclerView mRecyclerView;
    protected MultiItemTypeAdapter mAdapter;
    private EmptyWrapper emptyWrapper;
    protected Action  mAction;
    protected int mPage = 1;
    protected int mTotalPages = 1;
    public PtrFrameLayout mPtrFrame;
    @Override
    protected Class<BaseListDelegate> getDelegateClass() {
        return BaseListDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        mPtrFrame = viewDelegate.get(R.id.rotate_header_web_view_frame);
        mRecyclerView = viewDelegate.get(R.id.MyRecyclerView);
//        loadData();
        mAdapter = getAdapter();
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setPullRefreshEnabled(false);
        emptyWrapper = new EmptyWrapper(mAdapter);
//        emptyWrapper.setEmptyView(getEmptyView(null));

//        mRecyclerView.setAdapter(new SlideInLeftAnimationAdapter(emptyWrapper));
        mRecyclerView.setAdapter(emptyWrapper);
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

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mPtrFrame.setEnabled(topRowVerticalPosition >= 0);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        //因为XRecyclerView的下拉刷新关闭，使用ptrFrame的下拉刷新
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if(mRecyclerView.getLayoutManager() instanceof LinearLayoutManager
                        && ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition()==0){
                    return true;
                }else {
                    return false;
                }
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                BaseListFragment.this.onRefresh();
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 500);
            }
        });
    }

    protected RecyclerView.LayoutManager getLayoutManager(){
        //默认是LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayout.VERTICAL));
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
        isShowEmpty(datas.size());
        emptyWrapper.notifyDataSetChanged();
    }

    public  void clearData(){
        if(mAdapter!=null && mAdapter.getDatas()!=null) {
            mAdapter.getDatas().clear();
        }

    }

    protected  abstract MultiItemTypeAdapter getAdapter();

    public   void onRefresh(){
        mPage = 1;
        mAction = Action.REFRESH;
        loadData();
    }

    protected  void onLoadMore(){
        mPage ++;
        mAction = Action.LOADMORE;
        if(mPage<=mTotalPages){
            loadData();
        }else {
            Toast.makeText(getActivity(),"已到最后一页，没有更多数据！",Toast.LENGTH_SHORT).show();
        }
    }

    protected  View getEmptyView(){
        View emptyView  = View.inflate(getActivity(),R.layout.claims_empty_view,null);
        return emptyView;
    }

    protected void setEmptyView(Throwable e){
        if(emptyWrapper!=null){
            emptyWrapper.setEmptyView(getEmptyView(e));
            emptyWrapper.notifyDataSetChanged();
        }
    }

    public void isShowEmpty(int size){
        if (mTotalPages == 1 && size == 0){
            setEmptyView(null);
        }
    }

    protected  View getEmptyView(Throwable e){
        if (e!=null && (e instanceof SocketTimeoutException || e instanceof ConnectException
          || e instanceof UnknownHostException)) {  //这个异常暂时归到网络中断状态上来， 具体根据需求再定
            View noNetworkView  = View.inflate(getActivity(),R.layout.no_network_view,null);
            noNetworkView.findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRefresh();
                }
            });
            return noNetworkView;
        }else{
            return getEmptyView();
        }

    }

    @Override
    protected void checkIfLoadData() {
        if (isVisibleToUser && isViewInitialized) {
            isDataInitialized = true;
//            TODO load data
            onRefresh();
        }
    }

    public void disableLoadMore(){
        mRecyclerView.setLoadingMoreEnabled(false);
    }

    public void disableRefresh(){
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });
    }


    public enum  Action {
        REFRESH, LOADMORE
    }
}
