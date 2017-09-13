package com.framework.http.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.framework.http.ApiException;
import com.framework.model.Error;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by chengyuchun on 2016/8/22.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    private Gson mGson = new Gson();

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        Log.d("lang","request url:"+chain.request().url());
        if(originalResponse.code()!=200){
            String s = originalResponse.body().string();
            Error error = mGson.fromJson(s, Error.class);
            Log.d("lang","http code:"+originalResponse.code()+",error code:"+error.getCode() + ",error message:"+ error.getErrorMessage());
            throw new ApiException(error.getCode(), error.getErrorMessage());
        }
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.from(originalResponse.headers("Set-Cookie"))
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            String[] cookieArray = s.split(";");
                            return cookieArray[0];
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String cookie) {
                            cookieBuffer.append(cookie).append(";");
                        }
                    });
            SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            editor.commit();
        }
        return originalResponse;
    }
}
