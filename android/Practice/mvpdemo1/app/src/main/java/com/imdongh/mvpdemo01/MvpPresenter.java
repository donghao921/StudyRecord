package com.imdongh.mvpdemo01;

import com.imdongh.mvpdemo01.base.BasePresenter;
import com.imdongh.mvpdemo01.base.CallBack;

public class MvpPresenter extends BasePresenter<MvpView> {

    public void getData(final String params) {
        if (!isViewAttached()) {
            // 如果view为空就不加载数据
            return;
        }
        getView().showLoading();
//        MvpModel.getNetData(params, new MvpCallback() {
//            @Override
//            public void onSucc(Object data) {
//                if (isViewAttached()) {
//                    getView().showData((String) data);
//                }
//            }
//
//            @Override
//            public void onFail(String message) {
//                if (isViewAttached()) {
//                    getView().showFailureMessage(message);
//                }
//            }
//
//            @Override
//            public void onError(String error) {
//                if (isViewAttached()) {
//                    getView().showErrorMessage();
//                }
//            }
//
//            @Override
//            public void onComplete() {
//                if (isViewAttached()) {
//                    getView().hideLoading();
//                }
//            }
//        });
        DataModel.request(Token.API_USER_DATA)
                .params(params)
                .execute(new CallBack<String>() {
                    @Override
                    public void onSucc(String data) {
                        if (isViewAttached()) {
                            getView().showData(data);
                        }
                    }

                    @Override
                    public void onFail(String message) {
                        if (isViewAttached()) {
                            getView().showFailureMessage(message);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        if (isViewAttached()) {
                            getView().showErrorMessage();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (isViewAttached()){
                            getView().hideLoading();
                        }
                    }
                });

    }
}
