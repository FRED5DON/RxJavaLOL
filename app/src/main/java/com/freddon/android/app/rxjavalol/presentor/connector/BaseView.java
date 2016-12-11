package com.freddon.android.app.rxjavalol.presentor.connector;

/**
 *
 * @param <T>
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

    void showError(String msg);

}
