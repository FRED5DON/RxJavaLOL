package com.freddon.android.app.rxjavalol.presentor;

import com.freddon.android.app.rxjavalol.presentor.connector.BasePresenter;
import com.freddon.android.app.rxjavalol.presentor.connector.IExcutorPresenter;
import com.freddon.android.app.rxjavalol.presentor.connector.RxMiddleWare;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fred on 2016/12/7.
 */

public class ExcutorPresentor<T> extends RxMiddleWare implements BasePresenter<T>  {


    private CompositeSubscription mCompositeSubscription;

    @Override
    public void subscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }



    @Override
    public void attachView(T view) {

    }

    @Override
    public void storeView(T view) {

    }

    @Override
    public void detachView() {
        unSubscribe();
    }

}
