package com.framework.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chengyuchun on 2016/8/22.
 */
public class HeadInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().addHeader("test", "test").build();
        return chain.proceed(request);
    }
}
