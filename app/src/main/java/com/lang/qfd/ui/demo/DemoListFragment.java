package com.lang.qfd.ui.demo;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;
import com.framework.fragment.BaseListFragment;
import com.framework.http.DefaultTransformer;
import com.lang.R;
import com.lang.core.ITabFragment;
import com.lang.core.http.JsonApiWrapper;
import com.lang.core.subscribers.ProgressSubscriber;
import com.lang.qfd.model.BaseModel;
import com.lang.qfd.model.Benefit;
import com.lang.utils.GlideUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;

/**
 * Created by chengyuchun on 2017/3/7.
 */

public class DemoListFragment extends BaseListFragment implements ITabFragment {
    private ArrayList<Benefit> mArrayList = new ArrayList<Benefit>();
    private int mPage=1;
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        //默认是LinearLayoutManager，可以根据需要更改
        return super.getLayoutManager();
    }

    protected MultiItemTypeAdapter getAdapter(){
        return new CommonAdapter<Benefit>(getActivity(),R.layout.fragment_fu_list_item,mArrayList) {
            @Override
            protected void convert(ViewHolder holder, Benefit benefit, int position) {
                GlideUtil.displayImg((ImageView) holder.getView(R.id.mSampleListItemImg),benefit.url);
            }
        };
    }

    public void onRefresh(){
        mPage = 1;
        loadData();
    }

    public void onLoadMore(){
        mPage ++;
        if(mArrayList.size()==0)
            Toast.makeText(getActivity(),"没有可加载的数据",Toast.LENGTH_SHORT).show();
        loadData();
    }

    @Override
    protected void loadData(){
        JsonApiWrapper.serviceFu().rxBenefits(20, mPage)
                .compose(new DefaultTransformer<BaseModel<ArrayList<Benefit>>>()) //取网络数据这个函数不能少，这是线程切换
                .subscribe(new ProgressSubscriber<BaseModel<ArrayList<Benefit>>>(getContext(),false) {
                    @Override
                    public void onNext(BaseModel<ArrayList<Benefit>> arrayListBaseModel) {
                        mArrayList = arrayListBaseModel.results;
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
