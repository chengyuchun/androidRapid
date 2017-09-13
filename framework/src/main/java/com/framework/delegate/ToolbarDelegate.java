package com.framework.delegate;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.framework.R;
import com.kymjs.frame.presenter.ActivityPresenter;
import com.kymjs.frame.view.AppDelegate;

/**
 * Created by chengyuchun on 2017/4/17.
 *
 * 有toolbar的页面继承该类
 */

public abstract class ToolbarDelegate extends AppDelegate{
    private Toolbar mToolbar;

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.create(inflater,container, savedInstanceState);
        LinearLayout toolbarRootView = new LinearLayout(getActivity());
        toolbarRootView.setOrientation(LinearLayout.VERTICAL);

        mToolbar = (Toolbar) View.inflate(getActivity(), R.layout.base_toolbar, null);
        mToolbar.setTitle("");
        ((ActivityPresenter)getActivity()).setSupportActionBar(mToolbar);

        View divider = new View(this.getActivity());
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
        divider.setBackgroundColor(getActivity().getResources().getColor(R.color.gray_divide));

        toolbarRootView.addView(mToolbar);
        toolbarRootView.addView(divider);
        toolbarRootView.addView(rootView);
        rootView = toolbarRootView;
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView.findViewById(id);
            if(view == null)
                view = (T)getToolbar().findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    public void setTitle(String s){
        TextView textView = get(R.id.toolbar_title);
        textView.setText(s);
    }

//    public void setCalculateHead(){
//        ((ImageView)get(R.id.toolbar_right_img)).setImageResource(R.drawable.jsq);
//        get(R.id.ll_right).setVisibility(View.VISIBLE);
//        get(R.id.ll_right).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().startActivity(new Intent(getActivity(),CalculateActivity.class));
//            }
//        });
//    }

    public void setBackHead(){
        ((TextView)get(R.id.toolbar_left)).setText("");
        get(R.id.toolbar_left).setBackgroundResource(R.drawable.ic_fh);
        get(R.id.ll_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
