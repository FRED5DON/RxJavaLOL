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

public class OperatorFILTERExcutorPresentor extends ExcutorPresentor implements IExcutorPresenter.Presenter {


    private Label label;
    private IExcutorPresenter.View view;
    private Subscription observable;

    public OperatorFILTERExcutorPresentor(IExcutorPresenter.View view, Label label) {
        this.view = view;
        this.label = label;
        view.setRxLabelTitle(label.getText());
    }

    @Override
    public void excute() {
        view.onShow("开始执行");
        Integer[] data = {1, 3, 5, 6, 7};
        observable = Observable.from(data).subscribeOn(Schedulers.io())
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        //true 符合条件的
                        return integer % 2 != 0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer label) {
                        view.onShow(String.valueOf(label));
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
        return "Integer[] data = {1, 3, 5, 6, 7};\n" +
                "        Observable.from(data).subscribeOn(Schedulers.io())\n" +
                "                .filter(new Func1<Integer, Boolean>() {\n" +
                "                    @Override\n" +
                "                    public Boolean call(Integer integer) {\n" +
                "                        //true 符合条件的\n" +
                "                        return integer % 2 != 0;\n" +
                "                    }\n" +
                "                })\n" +
                "                .observeOn(AndroidSchedulers.mainThread())\n" +
                "                .subscribe(new Action1<Integer>() {\n" +
                "                    @Override\n" +
                "                    public void call(Integer label) {\n" +
                "                        view.onShow(String.valueOf(label));\n" +
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
