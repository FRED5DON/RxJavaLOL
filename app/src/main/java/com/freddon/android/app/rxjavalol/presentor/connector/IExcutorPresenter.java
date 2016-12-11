package com.freddon.android.app.rxjavalol.presentor.connector;

/**
 * Created by fred on 2016/12/7.
 */

public interface IExcutorPresenter {

    interface View extends BaseView<Presenter> {

        void onShow(CharSequence content);

        void alert(String content);

        void setSourceCode(CharSequence sourceCode);

        void setRxLabelTitle(String content);

    }

    interface Presenter extends BasePresenter {
        void excute();
        String getCode();
    }
}
