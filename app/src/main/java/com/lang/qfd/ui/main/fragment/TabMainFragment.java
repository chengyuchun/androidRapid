package com.lang.qfd.ui.main.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.framework.fragment.BaseFragmentPresenter;
import com.framework.http.DefaultTransformer;
import com.lang.R;
import com.lang.core.ITabFragment;
import com.lang.core.http.JsonApiWrapper;
import com.lang.core.subscribers.ProgressSubscriber;
import com.lang.qfd.model.BaseModel;
import com.lang.qfd.model.Benefit;
import com.lang.qfd.ui.demo.HomeActivity;
import com.lang.qfd.ui.main.delegate.TabMainDelegate;
import com.lang.widgets.banner.BannerView;

import java.util.ArrayList;

import rx.functions.Func1;

/**
 * Created by chengyuchun on 2016/11/2.
 */
public class TabMainFragment extends BaseFragmentPresenter<TabMainDelegate> implements ITabFragment {
    @Override
    protected Class<TabMainDelegate> getDelegateClass() {
        return TabMainDelegate.class;
    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getTitle() {
        return "main fragment";
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        ((HomeActivity)getActivity()).hiddenToolBar();
        viewDelegate.setRecyOrientation();
        refreshFuData();
        refreshTopBanner();

        final SwipeRefreshLayout swipeRefreshLayout = viewDelegate.get(R.id.mSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFuData();
                refreshTopBanner();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
        取json格式数据
     */
    private void refreshFuData() {
        JsonApiWrapper.serviceFu().rxBenefits(20, 1)
                .compose(new DefaultTransformer<BaseModel<ArrayList<Benefit>>>())  //取网络数据这个函数不能少，这是线程切换
                .subscribe(new ProgressSubscriber<BaseModel<ArrayList<Benefit>>>(getContext(), false) {
                    @Override
                    public void onNext(BaseModel<ArrayList<Benefit>> arrayListBaseModel) {
                        viewDelegate.refreshRecyclerViewData(arrayListBaseModel.results);
                    }
                });
    }

    private void refreshTopBanner() {
        final BannerView bannerView = viewDelegate.get(R.id.mBannerView);
        JsonApiWrapper.serviceFu().rxBenefits(3, 1)
                .compose(new DefaultTransformer<BaseModel<ArrayList<Benefit>>>())  //取网络数据这个函数不能少，这是线程切换
                .map(new Func1<BaseModel<ArrayList<Benefit>>, ArrayList<BannerView.Banner>>() {
                    @Override
                    public ArrayList<BannerView.Banner> call(BaseModel<ArrayList<Benefit>> arrayListBaseModel) {
                        ArrayList<BannerView.Banner> banners = new ArrayList<>();
                        for(Benefit topBanner:arrayListBaseModel.results){
                            banners.add(new BannerView.Banner(topBanner.url));
                        }
                        return banners;
                    }
                })
                .subscribe(new ProgressSubscriber<ArrayList<BannerView.Banner>>(getContext(),false) {
                    @Override
                    public void onNext(ArrayList<BannerView.Banner> banners) {
                        bannerView.setUpData(banners, new BannerView.OnBannerItemClickListener(){
                            @Override
                            public void onBannerClick(int index, BannerView.Banner banner) {

                            }
                        });
                    }
                });
    }
    /**
        取protobuffer格式数据
    */
//    private void refreshTopBanner(){
//        final BannerView bannerView = viewDelegate.get(R.id.mBannerView);
//        ProtoApiWrapper.serviceQfd().getTopBanner()
//                .compose(new DefaultTransformer<IndexProtos.TopBannerList>()) //取网络数据这个函数不能少，这是线程切换
//                .map(new Func1<IndexProtos.TopBannerList, ArrayList<BannerView.Banner>>() {
//                    @Override
//                    public ArrayList<BannerView.Banner> call(IndexProtos.TopBannerList topBannerList) {
//                        ArrayList<BannerView.Banner> banners = new ArrayList<>();
//                        for(IndexProtos.TopBannerList.TopBanner topBanner:topBannerList.getBannersList()){
//                            banners.add(new BannerView.Banner(topBanner.getImgUrl()));
//                        }
//                        return banners;
//                    }
//                })
//                .subscribe(new ProgressSubscriber<ArrayList<BannerView.Banner>>(getContext(),false) {
//                    @Override
//                    public void onNext(ArrayList<BannerView.Banner> banners) {
//                        bannerView.setUpData(banners, new BannerView.OnBannerItemClickListener(){
//                            @Override
//                            public void onBannerClick(int index, BannerView.Banner banner) {
//
//                            }
//                        });
//                    }
//                });
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden)
            ((HomeActivity)getActivity()).hiddenToolBar();
    }
}
