package com.lang.qfd.ui.invest.delegate;

import com.kymjs.frame.view.AppDelegate;
import com.lang.R;
import com.lang.qfd.ui.invest.adapter.ContactBean;
import com.lang.qfd.ui.invest.adapter.SortAdapter;
import com.lang.widgets.PinnedHeaderListView;
import java.util.List;

/**
 * Created by chengyuchun on 2017/3/16.
 */

public class TabInvestDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_tab_invest;
    }

    public void notifyListDataChange(List<ContactBean> list){
        PinnedHeaderListView listView = get(R.id.contact_listview);
        //随便写写，正式项目需要非空判断及注意效率
        SortAdapter adapter = new SortAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(adapter);
        listView.setPinnedHeaderView(getActivity().getLayoutInflater().inflate(R.layout.main_contact_title, listView, false));

    }
}
