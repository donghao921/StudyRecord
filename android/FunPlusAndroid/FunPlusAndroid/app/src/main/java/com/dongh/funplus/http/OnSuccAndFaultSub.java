package com.dongh.funplus.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.dongh.funplus.service.bean.BaseData;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by 眼神 on 2018/3/27.
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理   成功时 通过result是否等于1分别回调onSuccess和onFault，默认处理了401错误转登录。
 * 回调结果为String，需要手动序列化
 */

public class OnSuccAndFaultSub<T> extends DisposableObserver<BaseData<T>>
    implements ProgressCancelListener {
    /**
     * 是否需要显示默认Loading
     */
    private boolean showProgress = true;
    private OnSuccAndFaultListener mOnSuccAndFaultListener;

    private Context context;
    private ProgressDialog progressDialog;


    /**
     * @param mOnSuccAndFaultListener 成功回调监听
     */
    public OnSuccAndFaultSub(OnSuccAndFaultListener mOnSuccAndFaultListener) {
        this.mOnSuccAndFaultListener = mOnSuccAndFaultListener;
    }


    /**
     * @param mOnSuccAndFaultListener 成功回调监听
     * @param context 上下文
     */
    public OnSuccAndFaultSub(OnSuccAndFaultListener mOnSuccAndFaultListener, Context context) {
        this.mOnSuccAndFaultListener = mOnSuccAndFaultListener;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在加载中请稍后~");
    }


    /**
     * @param mOnSuccAndFaultListener 成功回调监听
     * @param context 上下文
     * @param showProgress 是否需要显示默认Loading
     */
    public OnSuccAndFaultSub(OnSuccAndFaultListener mOnSuccAndFaultListener, Context context, boolean showProgress) {
        this.mOnSuccAndFaultListener = mOnSuccAndFaultListener;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        this.showProgress = showProgress;
    }


    private void showProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog.show();
        }
    }


    private void dismissProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog.dismiss();
        }
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
    @Override public void onComplete() {
        dismissProgressDialog();
        progressDialog = null;
    }


    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     */
    @Override
    public void onError(Throwable e) {
        try {

            if (e instanceof SocketTimeoutException) {//请求超时
            } else if (e instanceof ConnectException) {//网络连接超时
                //                ToastManager.showShortToast("网络连接超时");
                mOnSuccAndFaultListener.onFault("网络连接超时");
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                //                ToastManager.showShortToast("安全证书异常");
                mOnSuccAndFaultListener.onFault("安全证书异常");
            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
                    //                    ToastManager.showShortToast("网络异常，请检查您的网络状态");
                    mOnSuccAndFaultListener.onFault("网络异常，请检查您的网络状态");
                } else if (code == 404) {
                    //                    ToastManager.showShortToast("请求的地址不存在");
                    mOnSuccAndFaultListener.onFault("请求的地址不存在");
                } else {
                    //                    ToastManager.showShortToast("请求失败");
                    mOnSuccAndFaultListener.onFault("请求失败");
                }
            } else if (e instanceof UnknownHostException) {//域名解析失败
                //                ToastManager.showShortToast("域名解析失败");
                mOnSuccAndFaultListener.onFault("域名解析失败");
            } else {
                //                ToastManager.showShortToast("error:" + e.getMessage());
                mOnSuccAndFaultListener.onFault("error:" + e.getMessage());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
            dismissProgressDialog();
            progressDialog = null;

        }

    }


    /**
     * 当result等于1回调给调用者，否则自动显示错误信息，若错误信息为401跳转登录页面。
     * ResponseBody  body = response.body();//获取响应体
     * InputStream inputStream = body.byteStream();//获取输入流
     * byte[] bytes = body.bytes();//获取字节数组
     * String str = body.string();//获取字符串数据
     */
    @Override
    public void onNext(BaseData baseData) {
        if (baseData != null) {
            int resultCode = baseData.getErrorCode();
            if (resultCode == 0) {
                mOnSuccAndFaultListener.onSuccess(baseData);
            } else {
                String errorMsg = baseData.getErrMsg();
                mOnSuccAndFaultListener.onFault(errorMsg);
                Log.e("OnSuccAndFaultSub", "errorMsg: " + errorMsg);
            }

        }
    }


    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }
}
