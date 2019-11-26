package com.test.chuanyi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:  zengfeng
 * Time  :  2019/10/24 17:54
 * Des   :
 */
public class RXJavaActivity extends Activity {
    Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava_layout);

        initRxJava();

        startActivity(new Intent(this,GreenDaoActivity.class));
    }

    private void initRxJava() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1--");
//                e.onError(new Exception("我是个异常"));
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                LogUtils.d("onSubscribe");
            }

            @Override
            public void onNext(String value) {
                LogUtils.v("onNext:" + value);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.e("onComplete");
            }
        };

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.d("accept -->" + s + "线程名：" + Thread.currentThread().getName());
            }
        };

        Consumer<Throwable> errorConsumer = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.e("errorConsumer -->"+throwable.getMessage());
            }
        };
//        observable.subscribe(consumer);
//        observable.subscribe(observer);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(consumer,errorConsumer);

        observable.map(new Function<Object, String>() {
            @Override
            public String apply(Object o) throws Exception {
                String s = "apply get value is："+o.toString();
                LogUtils.v(s);
                return s;
            }

        }).subscribe(consumer);
    }
}
