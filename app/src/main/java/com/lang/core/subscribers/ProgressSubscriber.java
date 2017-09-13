package com.lang.core.subscribers;

import android.content.Context;
import android.widget.Toast;

import com.framework.dialog.LoadingDialog;
import com.framework.http.ApiException;
import com.lang.core.progress.ProgressCancelListener;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

import static com.framework.http.ApiException.USER_EXPIRE;

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

//    private ProgressDialogHandler mProgressDialogHandler;
    private LoadingDialog mLoadingDialog;

    private Context context;

    private boolean showDialog = true;

    public ProgressSubscriber(Context context) {
        this.context = context;
//        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
        mLoadingDialog = new LoadingDialog(context);
    }

    public ProgressSubscriber(Context context, boolean show) {
        this.context = context;
        this.showDialog = show;
//        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
        mLoadingDialog = new LoadingDialog(context);
    }

    private void showProgressDialog(){
//        if (mProgressDialogHandler != null && showDialog) {
//            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
//        }
        if(mLoadingDialog !=null && showDialog)
            mLoadingDialog.show();
    }

    private void dismissProgressDialog(){
//        if (mProgressDialogHandler != null) {
//            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
//            mProgressDialogHandler = null;
//        }
        if(mLoadingDialog !=null && showDialog)
            mLoadingDialog.dismiss();
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if(e instanceof ApiException){
            if(((ApiException) e).getErrorCode() == USER_EXPIRE) {

            }
            Toast.makeText(context, "error:" + ((ApiException) e).getErrorCode() + e.getMessage(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}