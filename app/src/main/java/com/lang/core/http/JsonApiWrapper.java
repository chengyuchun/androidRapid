package com.lang.core.http;

import com.framework.http.ApiWrapper;
import com.framework.http.TrustSSLFactory;
import com.framework.http.interceptor.AddCookiesInterceptor;
import com.framework.http.interceptor.HeadInterceptor;
import com.framework.http.interceptor.ReceivedCookiesInterceptor;
import com.lang.api.FuApi;
import com.lang.core.CustomApplication;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.framework.http.TrustSSLFactory.DO_NOT_VERIFY;

/**
 * Created by chengyuchun on 2016/8/22.
 */
public class JsonApiWrapper extends ApiWrapper {

    //public static final String BASE_URL = "https://api.douban.com/v2/movie/";
//    public static final String BASE_URL = "https://jr-mobile.yingu.com";
    private static final String BASE_URL = "http://gank.io/";
    //public static final String BASE_URL = BuildConfig.SERVER_ADD;
    private FuApi api;
    private  static  final JsonApiWrapper mInstance = new JsonApiWrapper();

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
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Override
    protected void createApi() {
        api = retrofit.create(FuApi.class);
    }

    public static FuApi serviceFu(){
         return getInstance().api;
    }

    //获取单例
    public static JsonApiWrapper getInstance(){
        return mInstance;
    }
}
