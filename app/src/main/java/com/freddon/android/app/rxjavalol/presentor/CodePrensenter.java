package com.freddon.android.app.rxjavalol.presentor;

import com.freddon.android.app.rxjavalol.presentor.connector.ICodePresenter;

/**
 * Created by fred on 2016/12/9.
 */

public class CodePrensenter implements ICodePresenter.Presenter {


    private String html;
    private ICodePresenter.View view;

    public CodePrensenter(ICodePresenter.View view, String html) {
        this.view = view;
        this.html = html;
    }

    @Override
    public void excute() {
        this.view.onShow(html);
    }

    @Override
    public void attachView(Object view) {

    }

    @Override
    public void storeView(Object view) {

    }

    @Override
    public void detachView() {
        //不需要释放 没使用其他资源如rx
    }
}
