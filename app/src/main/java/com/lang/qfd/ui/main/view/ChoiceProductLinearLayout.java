package com.lang.qfd.ui.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.lang.R;

/**
 * Created by chengyuchun on 2016/11/10.
 */
public class ChoiceProductLinearLayout extends LinearLayout {
    public ChoiceProductLinearLayout(Context context) {
        super(context);
        init();
    }

    public ChoiceProductLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChoiceProductLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        inflate(getContext(), R.layout.widget_choice_product, this);
    }
}
