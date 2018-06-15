package com.nuwa.ui.main.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.framework.fragment.BaseFragmentPresenter;
import com.framework.http.DefaultTransformer;
import com.nuwa.R;
import com.nuwa.core.ITabFragment;
import com.nuwa.core.http.JsonApiWrapper;
import com.nuwa.core.subscribers.ProgressSubscriber;
import com.nuwa.model.BaseModel;
import com.nuwa.model.Benefit;
import com.nuwa.ui.main.delegate.TabMainDelegate;
import com.nuwa.widgets.banner.BannerView;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
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
        viewDelegate.setTitle("首页");
        viewDelegate.setRecyOrientation();

        final PtrFrameLayout mPtrFrame = viewDelegate.get(R.id.rotate_header_web_view_frame);
        mPtrFrame.setEnabled(false);

//        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollView, header);
                return true;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshFuData();
                refreshTopBanner();
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 500);
            }
        });

        // the following are default settings
//        mPtrFrame.setResistance(1.7f);
//        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
//        mPtrFrame.setDurationToClose(200);
//        mPtrFrame.setDurationToCloseHeader(1000);
//        // default is false
//        mPtrFrame.setPullToRefresh(false);
//        // default is true
//        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
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

}
