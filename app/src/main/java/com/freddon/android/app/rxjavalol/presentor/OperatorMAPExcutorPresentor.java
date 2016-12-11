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

public class OperatorMAPExcutorPresentor extends ExcutorPresentor implements IExcutorPresenter.Presenter {


    private Label label;
    private IExcutorPresenter.View view;
    private Subscription observable;

    public OperatorMAPExcutorPresentor(IExcutorPresenter.View view, Label label) {
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
                subscriber.onCompleted();

            }
        }).subscribeOn(Schedulers.io())
                .map(new Func1<Label, String>() {

                    @Override
                    public String call(Label label) {
                        //将Label对象转换为String发射出去 这期间在Schedulers.io执行
                        return label.getDesc();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String label) {
                        view.onShow("正在处理label，添加后缀`.EEE`");
                        //将Label对象转换为String发射出去 这期间在mainThread执行
                        return label + ".EEE";
                    }
                })
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
                })
        );
    }

    @Override
    public void attachView(Object view) {

    }

    @Override
    public void storeView(Object view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public String getCode() {
        return "Observable.create(new Observable.OnSubscribe<Label>() {\n" +
                "            @Override\n" +
                "            public void call(Subscriber<? super Label> subscriber) {\n" +
                "                subscriber.onNext(label);\n" +
                "                subscriber.onCompleted();\n" +
                "\n" +
                "            }\n" +
                "        }).subscribeOn(Schedulers.io())\n" +
                "                .map(new Func1<Label, String>() {\n" +
                "\n" +
                "                    @Override\n" +
                "                    public String call(Label label) {\n" +
                "                        //将Label对象转换为String发射出去 这期间在Schedulers.io执行\n" +
                "                        return label.getDesc();\n" +
                "                    }\n" +
                "                })\n" +
                "                .observeOn(AndroidSchedulers.mainThread())\n" +
                "                .map(new Func1<String, String>() {\n" +
                "                    @Override\n" +
                "                    public String call(String label) {\n" +
                "                        view.onShow(\"正在处理label，添加后缀`.EEE`\");\n" +
                "                        //将Label对象转换为String发射出去 这期间在mainThread执行\n" +
                "                        return label+\".EEE\";\n" +
                "                    }\n" +
                "                })\n" +
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
}
