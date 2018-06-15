package com.nuwa.ui.demo;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.framework.http.DefaultTransformer;
import com.nuwa.R;
import com.nuwa.core.BaseListFragment;
import com.nuwa.core.ITabFragment;
import com.nuwa.core.http.JsonApiWrapper;
import com.nuwa.core.subscribers.ProgressSubscriber;
import com.nuwa.model.BaseModel;
import com.nuwa.model.Benefit;
import com.nuwa.utils.GlideUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * Created by chengyuchun on 2017/3/7.
 *
 * 列表页的写法
 * 继承BaseListFragment，实现getAdapter、loadData方法即可
 * 实现接口ITabFragment不是必要的，只有当这个fragment是tabfragment的时候才需要实现这个接口
 */

public class DemoListFragment extends BaseListFragment implements ITabFragment {
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        //默认是LinearLayoutManager，可以根据需要更改
        return super.getLayoutManager();
    }

    protected MultiItemTypeAdapter getAdapter(){
        return new CommonAdapter<Benefit>(getActivity(),R.layout.fragment_fu_list_item,new ArrayList<Benefit>()) {
            @Override
            protected void convert(ViewHolder holder, Benefit benefit, int position) {
                GlideUtil.displayImg((ImageView) holder.getView(R.id.mSampleListItemImg),benefit.url);
            }
        };
    }


    @Override
    protected void loadData(){
        JsonApiWrapper.serviceFu().rxBenefits(20, mPage)
                .compose(new DefaultTransformer<BaseModel<ArrayList<Benefit>>>()) //取网络数据这个函数不能少，这是线程切换
                .subscribe(new ProgressSubscriber<BaseModel<ArrayList<Benefit>>>(getContext(),false) {
                    @Override
                    public void onNext(BaseModel<ArrayList<Benefit>> arrayListBaseModel) {
                        //刷新
                        if(mAction == Action.REFRESH){
                            clearData();
                        }
                        refreshData(arrayListBaseModel.results);
                    }
                });
    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public String getTitle() {
        return "这是一个Demo";
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
