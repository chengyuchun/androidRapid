package com.nuwa.core.http;

import com.framework.http.ApiWrapper;
import com.framework.http.TrustSSLFactory;
import com.framework.http.interceptor.AddCookiesInterceptor;
import com.framework.http.interceptor.HeadInterceptor;
import com.framework.http.interceptor.ReceivedCookiesInterceptor;
import com.nuwa.api.QfdApi;
import com.nuwa.core.CustomApplication;
import com.nuwa.core.http.converter.CustomProtoConverterFactory;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import static com.framework.http.TrustSSLFactory.DO_NOT_VERIFY;


/**
 * Created by chengyuchun on 2017/3/13.
 */

public class ProtoApiWrapper extends ApiWrapper {
    private static final String BASE_URL = "https://dev-api.qifadai.com";
    private  static  final ProtoApiWrapper mInstance = new ProtoApiWrapper();
    private QfdApi api;

    @Override
    protected void createOkClient() {
        okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new ReceivedCookiesInterceptor(CustomApplication.getContextObject())) //保存服务器返回的cookies
                .addInterceptor(new AddCookiesInterceptor(CustomApplication.getContextObject())) //带cookies访问
                .addInterceptor(new HeadInterceptor())
                .sslSocketFactory(TrustSSLFactory.getSocketFactory()) //忽略证书
                .hostnameVerifier(DO_NOT_VERIFY)
                .build();
    }

    @Override
    protected void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(CustomProtoConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Override
    protected void createApi() {
        api = retrofit.create(QfdApi.class);

    }

    public static QfdApi serviceQfd(){
        return getInstance().api;
    }

    //获取单例
    public static ProtoApiWrapper getInstance(){
        return mInstance;
    }
}
