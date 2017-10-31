package com.framework.http.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by chengyuchun on 2016/8/22.
 */
public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("access_token", Context.MODE_PRIVATE);

        SharedPreferences sharedPreferencesCookie = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        Observable.just(sharedPreferences.getString("access_token", ""))
        .subscribe(new Action1<String>() {
            @Override
            public void call(String access_token) {
                //添加cookie
                builder.addHeader("Authorization", access_token);
                builder.addHeader("User-Agent", "mobile");
                builder.addHeader("Test", access_token);
            }
        });
        Observable.just(sharedPreferencesCookie.getString("cookie", ""))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String cookie) {
                        //添加cookie
                        builder.addHeader("Cookie", cookie);
                    }
                });
        return chain.proceed(builder.build());
    }
}