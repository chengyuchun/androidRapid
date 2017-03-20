package com.lang.widgets.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lang.R;
import com.lang.utils.GlideUtil;
import com.lang.utils.L;

import java.util.ArrayList;

public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private ViewPager mBannerPager;
    private DotView mBannerDotView;
    private ArrayList<Banner> banners;
    private BannerAdapter adapter;
    private int position;
    private static final long BANNER_SCROLL_INTERVAL = 2 * 1000;
    private OnBannerItemClickListener listener;

    public BannerView(Context context) {
        super(context);
        setUpView();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    private void setUpView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_banner_view, this);
        mBannerPager = (ViewPager) findViewById(R.id.mBannerPager);
        mBannerPager.addOnPageChangeListener(this);
        mBannerDotView = (DotView) findViewById(R.id.mBannerDotView);
    }

    public void setUpData(ArrayList<Banner> banners, OnBannerItemClickListener listener) {
        this.banners = banners;
        this.listener = listener;
        adapter = new BannerAdapter();
        mBannerPager.setAdapter(adapter);
        int half = Short.MAX_VALUE / 2;
        half = half - half % banners.size();
        mBannerPager.setCurrentItem(half);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            L.e("banner switch");
            if (position < Short.MAX_VALUE - 1){
                mBannerPager.setCurrentItem(position + 1, true);
            }
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        handler.removeMessages(0);
        this.position = position;
        mBannerDotView.notifyDataChanged(position % banners.size(), banners.size());
        handler.sendEmptyMessageDelayed(0, BANNER_SCROLL_INTERVAL);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onStart() {
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, BANNER_SCROLL_INTERVAL);
    }

    public void onStop() {
        handler.removeMessages(0);
    }

    class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Short.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int index = position % banners.size();
            final Banner banner = banners.get(index);
            final ImageView imageView = new ImageView(getContext());
            //imageView.setImageResource(banner.resId);
            GlideUtil.displayImg(imageView,"https:"+banner.url);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBannerClick(index, banner);
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public interface OnBannerItemClickListener {
        void onBannerClick(int index, Banner banner);
    }

    public static class Banner {
        public int resId;
        public String title;
        public String url;

        public Banner(int resId) {
            this.resId = resId;
        }
        public Banner(String url) {
            this.url = url;
        }
    }

}
