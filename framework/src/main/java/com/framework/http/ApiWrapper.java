package com.framework.http;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.framework.http.TrustSSLFactory.DO_NOT_VERIFY;

/**
 * Created by chengyuchun on 2016/8/22.
 */
public class ApiWrapper {
    protected static final int DEFAULT_TIMEOUT = 5;
    protected OkHttpClient okHttpClient;
    protected Retrofit retrofit;
    private  static  final ApiWrapper mInstance = new ApiWrapper();

    //构造方法私有
    public ApiWrapper() {
        //手动创建一个OkHttpClient并设置超时时间
        createOkClient();
        createRetrofit();
        createApi();
    }


    protected void createOkClient(){
        okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(TrustSSLFactory.getSocketFactory()) //忽略证书
                .hostnameVerifier(DO_NOT_VERIFY)
                .build();
    }

    protected void createRetrofit(){

    }


    protected void createApi() {

    }
    public  OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    //获取单例
    public static ApiWrapper getInstance(){
        return mInstance;
    }


}
