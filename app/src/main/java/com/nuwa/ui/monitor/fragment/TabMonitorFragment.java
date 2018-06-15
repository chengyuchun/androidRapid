package com.nuwa.ui.monitor.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.framework.fragment.BaseFragmentPresenter;
import com.nuwa.R;
import com.nuwa.core.ITabFragment;
import com.nuwa.ui.monitor.delegate.TabMonitorDelegate;
import com.nuwa.ui.monitor.service.RemindService;

/**
 * Created by chengyuchun on 2016/11/2.
 */
public class TabMonitorFragment extends BaseFragmentPresenter<TabMonitorDelegate> implements ITabFragment {
    MinerStatusReceiver mMinerStatusReceiver;
    private StringBuffer log = new StringBuffer();
    @Override
    protected Class<TabMonitorDelegate> getDelegateClass() {
        return TabMonitorDelegate.class;
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
        return "monitor fragment";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.miner.status");
        if(mMinerStatusReceiver == null) {
            mMinerStatusReceiver = new MinerStatusReceiver();
            getActivity().registerReceiver(mMinerStatusReceiver, intentFilter);
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();

        viewDelegate.get(R.id.btn_monitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此比特大陆B3矿机监控服务,不需要请关闭
                Intent intent = new Intent(getActivity(),RemindService.class);
                getContext().startService(intent);
            }
        });
    }

    class MinerStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.miner.status")){
                Bundle bundle = intent.getExtras();//获取数据
                String data = bundle.getString("data");
                ((TextView)viewDelegate.get(R.id.tv_log)).setText(data);
                ((Button)viewDelegate.get(R.id.btn_monitor)).setText("比特大陆B3矿机监控服务已经开启！3秒轮询一次！");
                viewDelegate.get(R.id.btn_monitor).setClickable(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMinerStatusReceiver!=null){
            getActivity().unregisterReceiver(mMinerStatusReceiver);
        }
    }
}
