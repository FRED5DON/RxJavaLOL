package com.freddon.android.app.rxjavalol.ui.activities;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by fred on 2016/12/7.
 */

public abstract class BaseActivity<T> extends AppCompatActivity {

    public void setmPresenter(T mPresenter) {
        this.mPresenter = mPresenter;
    }

    protected T mPresenter;



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
