package com.freddon.android.app.rxjavalol.presentor;

import com.freddon.android.app.rxjavalol.model.Label;
import com.freddon.android.app.rxjavalol.presentor.connector.IExcutorPresenter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by fred on 2016/12/7.
 */

public class OperatorIntervalExcutorPresentor extends ExcutorPresentor implements IExcutorPresenter.Presenter {


    private Label label;
    private IExcutorPresenter.View view;
    private Subscription observable;

    public OperatorIntervalExcutorPresentor(IExcutorPresenter.View view, Label label) {
        this.view = view;
        this.label = label;
        view.setRxLabelTitle(label.getText());
    }

    @Override
    public void excute() {
        view.onShow("每隔1秒执行");
        observable = Observable.interval(1, TimeUnit.SECONDS)
                .limit(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        view.onShow(String.valueOf(aLong));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        view.onShow("OnComplete 执行完成");
                        delayInfo();
                    }
                });
        subscribe(observable);
    }

    public void delayInfo() {
        subscribe(Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        view.onShow("observable是否被自动取消订阅? " + (observable.isUnsubscribed() ? "是" : "否"));
                    }
                }));
    }


    @Override
    public String getCode() {
        return "view.onShow(\"每隔1秒执行\");\n" +
                "        Observable.interval(1, TimeUnit.SECONDS)\n" +
                "                .limit(10)\n" +
                "                .subscribeOn(Schedulers.io())\n" +
                "                .observeOn(AndroidSchedulers.mainThread())\n" +
                "                .subscribe(new Action1<Long>() {\n" +
                "                    @Override\n" +
                "                    public void call(Long aLong) {\n" +
                "                        view.onShow(String.valueOf(aLong));\n" +
                "                    }\n" +
                "                }, new Action1<Throwable>() {\n" +
                "                    @Override\n" +
                "                    public void call(Throwable throwable) {\n" +
                "                    }\n" +
                "                }, new Action0() {\n" +
                "                    @Override\n" +
                "                    public void call() {\n" +
                "                        view.onShow(\"OnComplete 执行完成\");\n" +
                "                        delayInfo();\n" +
                "                    }\n" +
                "                });";
    }

}
