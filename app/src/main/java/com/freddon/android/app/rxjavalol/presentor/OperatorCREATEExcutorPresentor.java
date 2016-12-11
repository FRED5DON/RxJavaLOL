package com.freddon.android.app.rxjavalol.presentor;

import com.freddon.android.app.rxjavalol.model.Label;
import com.freddon.android.app.rxjavalol.presentor.connector.IExcutorPresenter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by fred on 2016/12/7.
 */

public class OperatorCREATEExcutorPresentor extends ExcutorPresentor implements IExcutorPresenter.Presenter {


    private Label label;
    private IExcutorPresenter.View view;
    private Subscription observable;

    public OperatorCREATEExcutorPresentor(IExcutorPresenter.View view, Label label) {
        this.view = view;
        this.label = label;
        view.setRxLabelTitle(label.getText());
    }

    @Override
    public void excute() {
        view.onShow("开始执行");
        observable = Observable.create(new Observable.OnSubscribe<Label>() {
            @Override
            public void call(Subscriber<? super Label> subscriber) {
                subscriber.onNext(label);
                subscriber.onNext(new Label("HELLO WORD,NOT WORLD!"));
                subscriber.onCompleted();

            }
        }).subscribeOn(Schedulers.io())
                .map(new Func1<Label, String>() {

                    @Override
                    public String call(Label label) {
                        //将Label对象转换为String发射出去
                        return label.getDesc();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String label) {
                        view.onShow("订阅到Label事件");
                        view.onShow(label);
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
                    }
                });
        subscribe(observable);
        delayInfo();
    }

    @Override
    public String getCode() {
        return "Observable.create(new Observable.OnSubscribe<Label>() {\n" +
                "            @Override\n" +
                "            public void call(Subscriber<? super Label> subscriber) {\n" +
                "                subscriber.onNext(label);\n" +
                "                subscriber.onNext(new Label(\"HELLO WORD,NOT WORLD!\"));\n" +
                "                subscriber.onCompleted();\n" +
                "\n" +
                "            }\n" +
                "        }).subscribeOn(Schedulers.io())\n" +
                "                .map(new Func1<Label, String>() {\n" +
                "\n" +
                "                    @Override\n" +
                "                    public String call(Label label) {\n" +
                "                        //将Label对象转换为String发射出去\n" +
                "                        return label.getDesc();\n" +
                "                    }\n" +
                "                })\n" +
                "                .observeOn(AndroidSchedulers.mainThread())\n" +
                "                .subscribe(new Action1<String>() {\n" +
                "                    @Override\n" +
                "                    public void call(String label) {\n" +
                "                        view.onShow(\"订阅到Label事件\");\n" +
                "                        view.onShow(label);\n" +
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
                "                        delayInfo();\n" +
                "                    }\n" +
                "                })";
    }

    public void delayInfo() {
        Subscription timer = Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        observable.unsubscribe();
                        view.onShow("observable是否被自动取消订阅? " + (observable.isUnsubscribed() ? "是" : "否"));
                    }
                });
        subscribe(timer);
    }
}
