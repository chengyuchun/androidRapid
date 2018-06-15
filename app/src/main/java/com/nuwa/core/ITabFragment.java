package com.nuwa.core;

import android.support.v4.app.Fragment;

public interface ITabFragment {

    void onMenuItemClick();

    Fragment getFragment();

    String getTitle();
}
