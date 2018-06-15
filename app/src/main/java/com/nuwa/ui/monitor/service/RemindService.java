package com.nuwa.ui.monitor.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class RemindService extends Service {
  
    private MediaPlayer mMediaPlayer = null;
    private Handler mHandler;

    @Override  
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub  
        return null;  
    }  
    @Override  
    public void onCreate() {  
        // TODO Auto-generated method stub  
        super.onCreate();  
    }  
  
    @Override  
    public void onDestroy() {  
        // TODO Auto-generated method stub  
          
        if(mMediaPlayer != null){  
            mMediaPlayer.stop();  
            // 要释放资源，不然会打开很多个MediaPlayer  
            mMediaPlayer.release();  
        }
          
        super.onDestroy();
    }  

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")  
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {  
        // TODO Auto-generated method stub

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mHandler = new Handler(Looper.myLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what){
                            case 4:
                                try {
                                    Log.i("cycloid","BTM矿机状态为4，即售鎜状态，继续轮询....");
                                    Intent intent = new Intent("com.miner.status");
                                    intent.putExtra("data",getCurrrentTime()+",BTM矿机状态为4，即售鎜状态，继续轮询....");
                                    getApplicationContext().sendBroadcast(intent);

                                    Thread.sleep(3000);
                                    loadData();
                                }catch (InterruptedException e){
                                    e.printStackTrace();
                                }

                                break;

                            case -1:
                                try {
                                    Log.i("cycloid","访问失败，继续轮询....");
                                    Intent intent = new Intent("com.miner.status");
                                    intent.putExtra("data",getCurrrentTime()+",访问失败，继续轮询....");
                                    getApplicationContext().sendBroadcast(intent);

                                    Thread.sleep(3000);
                                    loadData();
                                }catch (InterruptedException e){
                                    e.printStackTrace();
                                }

                                break;
                            default:
                                //响铃
                                Log.i("cycloid","BTM矿机状态为"+msg.what+"，也许可以订购，请马上抢购！！！");
                                Intent intent = new Intent("com.miner.status");
                                intent.putExtra("data",getCurrrentTime()+",BTM矿机状态为"+msg.what+"，也许可以订购，请马上抢购！！！");
                                getApplicationContext().sendBroadcast(intent);

                                Context context = getApplicationContext();
                                AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                playSound(context);
                                break;
                        }
                    }
                };
                Looper.loop();
            }
        }).start();

        //定时查询数据
        loadData();
        return super.onStartCommand(intent, flags, startId);  
    }

    protected void loadData(){
        String url = "https://shop-product.bitmain.com.cn/api/product/";
        //获取Retrofit对象，设置地址
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        RequestServices requestServices = retrofit.create(RequestServices.class);
        Call<ResponseBody> call = requestServices.getString();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getJSONObject("data").getInt("productStatus");
                        Message msg = new Message();
                        msg.what = status;
                        mHandler.sendMessage(msg);
                    }catch (JSONException e){
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Message msg = new Message();
                msg.what = -1;
                mHandler.sendMessage(msg);
            }
        });
    }
  
    private void playSound(final Context context) {
          
        Log.e("ee", "正在响铃");
        // 使用来电铃声的铃声路径
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        // 如果为空，才构造，不为空，说明之前有构造过
        if(mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, uri);
            mMediaPlayer.setLooping(true); //循环播放
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String getCurrrentTime() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    public interface RequestServices {
        //请求方式为GET，参数为basil2style，因为没有变量所以下面getString方法也不需要参数
        @GET("getDetail?productId=00020180502145112313SjWrFvwU06F5")
        //定义返回的方法，返回的响应体使用了ResponseBody
        Call<ResponseBody> getString();
    }
}  