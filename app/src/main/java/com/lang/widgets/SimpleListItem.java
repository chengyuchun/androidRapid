package com.lang.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lang.R;

public class SimpleListItem extends LinearLayout {

    public static final int LINE_TYPE_NONE = 0; // 不显示线
    public static final int LINE_TYPE_DOT = 1;  // 虚线
    public static final int LINE_TYPE_SOLID = 2;// 实线

    private ImageView mImgIcon, mImgArrow, mImgDelete;
    private TextView mTvTitle, mTvTip;
    private EditText etInput;

    private View solidTop, dotTop, solidBottom, dotBottom;

    public SimpleListItem(Context context) {
        this(context, null);
    }

    public SimpleListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        inflate(getContext(), R.layout.widget_text_list_arrow_item, this);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleListItem);

        int icon = typedArray.getResourceId(R.styleable.SimpleListItem_itemIcon, -1);
        boolean arrowDisplay = typedArray.getBoolean(R.styleable.SimpleListItem_arrowDisplay, true);
        String title = typedArray.getString(R.styleable.SimpleListItem_titleText);
        int titleColor = typedArray.getColor(R.styleable.SimpleListItem_titleColor, Color.BLACK);
        String content = typedArray.getString(R.styleable.SimpleListItem_contentText);
        int contentColor = typedArray.getColor(R.styleable.SimpleListItem_contentColor, Color.BLACK);
        boolean contentDisplay = typedArray.getBoolean(R.styleable.SimpleListItem_contentDisplay, true);
        int topLineType = typedArray.getInt(R.styleable.SimpleListItem_topLine, 0);
        int bottomLineType = typedArray.getInt(R.styleable.SimpleListItem_bottomLine, 0);

        float titleTxtSize = typedArray.getDimension(R.styleable.SimpleListItem_titleTxtSize, getThemeDefaultTextSize());
        float etTxtSize = typedArray.getDimension(R.styleable.SimpleListItem_etTxtSize, getThemeDefaultTextSize());
        float contentTxtSize = typedArray.getDimension(R.styleable.SimpleListItem_contentTxtSize, getThemeDefaultTextSize());
        int titleLeftSpace = typedArray.getDimensionPixelSize(R.styleable.SimpleListItem_titleLeftSpaceExtra, 0);

        boolean titleDisplay = typedArray.getBoolean(R.styleable.SimpleListItem_titleDisplay, true);

        boolean deleteDisplay = typedArray.getBoolean(R.styleable.SimpleListItem_deleteDisplay, false);

        boolean etDisplay = typedArray.getBoolean(R.styleable.SimpleListItem_etDisplay, false);
        String hint = typedArray.getString(R.styleable.SimpleListItem_etHint);

        mImgIcon = (ImageView) findViewById(R.id.item_icon);
        mImgArrow = (ImageView) findViewById(R.id.img_arrow);
        mImgDelete = (ImageView) findViewById(R.id.img_delete);

        mTvTitle = (TextView) findViewById(R.id.tv_itemTitle);
        mTvTip = (TextView) findViewById(R.id.tv_itemContent);

        mTvTitle.setVisibility(titleDisplay ? VISIBLE : GONE);

        etInput = (EditText) findViewById(R.id.et_input);
        etInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, etTxtSize);

        dotTop = findViewById(R.id.top_dot_line);
        solidTop = findViewById(R.id.top_solid_line);
        dotBottom = findViewById(R.id.bottom_dot_line);
        solidBottom = findViewById(R.id.bottom_solid_line);

        setLineType(topLineType, dotTop, solidTop);
        setLineType(bottomLineType, dotBottom, solidBottom);

        mTvTitle.setText(title);
        mTvTitle.setTextColor(titleColor);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTxtSize);

        mTvTip.setText(content);
        mTvTip.setTextColor(contentColor);
        mTvTip.setVisibility(contentDisplay ? VISIBLE : GONE);
        mTvTip.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTxtSize);

        mImgArrow.setVisibility(arrowDisplay ? VISIBLE : GONE);
        mImgDelete.setVisibility(deleteDisplay ? VISIBLE : GONE);

        setupDelete(mImgDelete);
        iconSetup(icon);
        editTextSetup(etDisplay, hint);
        setTitleLeftSpace(titleLeftSpace);

        typedArray.recycle();
    }

    public void setTitleText(String titleString) {
        mTvTitle.setText(titleString);
    }

    public void setContentText(String tipString) {
        mTvTip.setText(tipString);
    }

    private void iconSetup(int icon) {
        if (icon >= 0) {
            mImgIcon.setVisibility(VISIBLE);
            mImgIcon.setImageResource(icon);
        } else {
            mImgIcon.setVisibility(GONE);
        }
    }

    private void editTextSetup(boolean display, String hint) {
        etInput.setHint(hint);
        etInput.setVisibility(display ? VISIBLE : GONE);
    }

    private void setLineType(int type, View dotLine, View solidLine) {

        if (type == LINE_TYPE_NONE) {
            dotLine.setVisibility(GONE);
            solidLine.setVisibility(GONE);
        } else if (type == LINE_TYPE_DOT) {
            solidLine.setVisibility(GONE);
            dotLine.setVisibility(VISIBLE);
        } else if (type == LINE_TYPE_SOLID) {
            solidLine.setVisibility(VISIBLE);
            dotLine.setVisibility(GONE);
        }
    }

    public EditText getEditText() {
        return this.etInput;
    }

    public void setTitle(String strTitle) {
        mTvTitle.setText(strTitle);
    }

    public void setContentTextDisplay(int visible) {
        mTvTip.setVisibility(visible);
    }

    public void setArrowDisplay(boolean visible) {
        mImgArrow.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setListItemType() {
        setLineType(LINE_TYPE_DOT, dotBottom, solidBottom);
        setLineType(LINE_TYPE_NONE, dotTop, solidTop);
    }

    private void setupDelete(View deleteView) {
        deleteView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (etInput != null) {
                    etInput.setText("");
                }
            }
        });
    }

    private void setTitleLeftSpace(int padding) {
        mTvTitle.setPadding(padding, 0, 0, 0);

    }

    public float getThemeDefaultTextSize() {

        return getResources().getDimension(R.dimen.x26);
    }

    public String getInputText() {
        return etInput.getText().toString();
    }

    public void setMaxInputLenth(int lenth) {
        etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lenth)});
    }

    public TextView getContentText() {
        return mTvTip;
    }
}
