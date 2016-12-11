package com.freddon.android.app.rxjavalol.presentor.connector;


/**
 * @param <T>
 */
public interface BasePresenter<T> {

    void attachView(T view);

    void storeView(T view);

    void detachView();
}
