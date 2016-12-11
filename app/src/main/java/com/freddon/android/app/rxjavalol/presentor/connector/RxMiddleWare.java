package com.freddon.android.app.rxjavalol.presentor.connector;

import rx.Subscription;

/**
 * Created by fred on 2016/12/8.
 */

public abstract class RxMiddleWare {

    public void subscribe() {

    }

    public void unSubscribe() {

    }


    public abstract void subscribe(Subscription subscription);
}
