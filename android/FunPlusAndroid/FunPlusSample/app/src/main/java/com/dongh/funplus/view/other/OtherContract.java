package com.dongh.funplus.view.other;

import com.dongh.funplus.event.MessageEvent;
import com.dongh.funplus.base.mvp.IModel;
import com.dongh.funplus.base.mvp.IPresenter;
import com.dongh.funplus.base.mvp.IView;

/**
 * Created by chenxz on 2017/12/3.
 */

public interface OtherContract {

    interface View extends IView {
        void setMessage(MessageEvent messageEvent);
    }

    interface Model extends IModel {

    }

    interface Presenter extends IPresenter {
        void sendMessage();
    }

}
