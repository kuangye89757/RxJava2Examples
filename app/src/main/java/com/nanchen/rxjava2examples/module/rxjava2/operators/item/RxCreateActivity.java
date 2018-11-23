package com.nanchen.rxjava2examples.module.rxjava2.operators.item;

import android.annotation.SuppressLint;
import android.util.Log;

import com.nanchen.rxjava2examples.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * create 操作符
 *
 * 主要用于产生一个 Obserable 被观察者对象，
 *
 * 被观察者 Observable 称为发射器（上游事件），观察者 Observer 称为接收器（下游事件）。
 * 
 * RxJava 2.x 与 1.x 区别:
 *      1.创建 Observable 时，回调的是 ObservableEmitter (发射器) 且抛异常
 *      
 *      2.创建 Observer 时，多了一个回调方法：onSubscribe(参数:Disposable)
 *              Disposable 相当于 RxJava 1.x 中的 Subscription， 用于解除订阅
 *      
 *      3.Consumer 即消费者，用于接收单个值
 *        BiConsumer 则是接收两个值
 *        Function 用于变换对象
 *        Predicate 用于判断
 *        
 * @author shijie9
 */
public class RxCreateActivity extends RxOperatorBaseActivity {
    private static final String TAG = "RxCreateActivity";

    @Override
    protected String getSubTitle() {
        return getString(R.string.rx_create);
    }

    @Override
    protected void doSomething() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1" + "\n");
                Log.e(TAG, "Observable emit 1" + "\n");
                e.onNext(1);
                mRxOperatorsText.append("Observable emit 2" + "\n");
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                mRxOperatorsText.append("Observable emit 3" + "\n");
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                mRxOperatorsText.append("Observable emit 4" + "\n");
                Log.e(TAG, "Observable emit 4" + "\n" );
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxOperatorsText.append("onSubscribe : " + d.isDisposed() + "\n");
                Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n" );
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                mRxOperatorsText.append("onNext : value : " + integer + "\n");
                Log.e(TAG, "onNext : value : " + integer + "\n" );
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                    mRxOperatorsText.append("onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                    Log.e(TAG, "onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mRxOperatorsText.append("onError : value : " + e.getMessage() + "\n");
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n" );
            }

            @Override
            public void onComplete() {
                mRxOperatorsText.append("onComplete" + "\n");
                Log.e(TAG, "onComplete" + "\n" );
            }
        });
    }


    
    @SuppressLint("CheckResult")
    @Override
    protected void doOtherthing() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) {
                mRxOperatorsText.append("Observable emit 1 \n");
                e.onNext(1);

                mRxOperatorsText.append("Observable emit 2 \n");
                e.onNext(2);

                mRxOperatorsText.append("Observable emit 3 \n");
                e.onNext(3);
                e.onComplete();

                mRxOperatorsText.append("Observable emit 4 \n");
                e.onNext(4);
                mRxOperatorsText.append("Observable emit 5 \n");
                e.onNext(5);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                mRxOperatorsText.append("onNext : value = " + integer + "\n");
            }
        });
    }
}
