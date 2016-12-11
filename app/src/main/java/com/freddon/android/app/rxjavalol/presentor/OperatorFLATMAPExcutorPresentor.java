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

public class OperatorFLATMAPExcutorPresentor extends ExcutorPresentor implements IExcutorPresenter.Presenter {


    private Label label;
    private IExcutorPresenter.View view;
    private Subscription observable;

    public OperatorFLATMAPExcutorPresentor(IExcutorPresenter.View view, Label label) {
        this.view = view;
        this.label = label;
        view.setRxLabelTitle(label.getText());
    }

    @Override
    public void excute() {
        view.onShow("开始执行");
        observable = Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                //将生成的Observable 映射成一个或多个Observable【铺平再发射出去】
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer index) {
                        //将index+1 这期间在Schedulers.io执行
                        return Observable.just(index + 1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer index) {
                        view.onShow(String.valueOf(index));
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
        return "view.onShow(\"开始执行\");\n" +
                "        Observable.just(1, 2, 3)\n" +
                "                .subscribeOn(Schedulers.io())\n" +
                "                //将生成的Observable 映射成一个或多个Observable【铺平再发射出去】\n" +
                "                .flatMap(new Func1<Integer, Observable<Integer>>() {\n" +
                "                    @Override\n" +
                "                    public Observable<Integer> call(Integer index) {\n" +
                "                        //将index+1 这期间在Schedulers.io执行\n" +
                "                        return Observable.just(index + 1);\n" +
                "                    }\n" +
                "                })\n" +
                "                .observeOn(AndroidSchedulers.mainThread())\n" +
                "                .subscribe(new Action1<Integer>() {\n" +
                "                    @Override\n" +
                "                    public void call(Integer index) {\n" +
                "                        view.onShow(String.valueOf(index));\n" +
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
                "                });";
    }
}
