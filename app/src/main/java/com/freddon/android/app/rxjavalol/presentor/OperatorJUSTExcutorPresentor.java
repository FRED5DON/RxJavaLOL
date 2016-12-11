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

public class OperatorJUSTExcutorPresentor extends ExcutorPresentor implements IExcutorPresenter.Presenter {


    private Label label;
    private IExcutorPresenter.View view;
    private Subscription observable;

    public OperatorJUSTExcutorPresentor(IExcutorPresenter.View view, Label label) {
        this.view = view;
        this.label = label;
        view.setRxLabelTitle(label.getText());
    }

    @Override
    public void excute() {
        view.setRxLabelTitle(label.getText());
        view.onShow("开始执行");
        observable = Observable.just(label,new Label("HELLO WORD,NOT WORLD!"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Label>() {
                    @Override
                    public void call(Label label) {
                        view.onShow("订阅到Label事件");
                        view.onShow(label.getDesc());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.onShow("出现错误");
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        view.onShow("执行结束");
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
        return "Observable.just(label)\n" +
                "                .subscribeOn(Schedulers.io())\n" +
                "                .observeOn(AndroidSchedulers.mainThread())\n" +
                "                .subscribe(new Action1<Label>() {\n" +
                "                    @Override\n" +
                "                    public void call(Label label) {\n" +
                "                        view.alert(\"订阅到Label事件\");\n" +
                "                        view.onShow(label.getDesc());\n" +
                "                    }\n" +
                "                }, new Action1<Throwable>() {\n" +
                "                    @Override\n" +
                "                    public void call(Throwable throwable) {\n" +
                "                        view.onShow(\"出现错误\");\n" +
                "                    }\n" +
                "                }, new Action0() {\n" +
                "                    @Override\n" +
                "                    public void call() {\n" +
                "                        view.onShow(\"执行结束\");\n" +
                "                    }\n" +
                "                });";
    }
}
