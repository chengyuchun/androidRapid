package com.framework.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;
import com.framework.R;
/**
 * Created by chengyuchun on 2017/7/13.
 *
 * 一个activity只包含一个fragment的时候，使用该delegate
 */

public class FragmentContainerDelegate extends BackToolbarDelegate{
    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_container;
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = ((FragmentActivity)getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void setContainerMargins(int left,int top,int right,int bottom){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(left, top, right, bottom);
        get(R.id.fragment_container).setLayoutParams(lp);
    }

}
