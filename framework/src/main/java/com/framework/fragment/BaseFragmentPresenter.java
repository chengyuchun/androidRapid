package com.framework.fragment;

import android.os.Bundle;
import android.view.View;

import com.kymjs.frame.presenter.FragmentPresenter;
import com.kymjs.frame.view.IDelegate;

/**
 * Created by chengyuchun on 2017/3/7.
 */
public abstract class BaseFragmentPresenter<T extends IDelegate> extends FragmentPresenter<T> {

    protected boolean isVisibleToUser;
    protected boolean isViewInitialized;
    protected boolean isDataInitialized;
    protected boolean isLazyLoadEnabled;

    protected  void loadData(){};

    public void enableLazyLoad(){
        isLazyLoadEnabled = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        checkIfLoadData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isLazyLoadEnabled){
            loadData();
        }else {
            isViewInitialized = true;
            if (savedInstanceState != null){
                onRestoreInstanceState(savedInstanceState);
            }
            checkIfLoadData();
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isDataInitialized = true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInitialized = false;
    }

    protected void checkIfLoadData() {
        if (isVisibleToUser && isViewInitialized ) {
            isDataInitialized = true;
//            TODO load data
            loadData();
        }
    }

}
