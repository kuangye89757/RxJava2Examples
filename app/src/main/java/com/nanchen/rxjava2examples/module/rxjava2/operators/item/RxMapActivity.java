package com.nanchen.rxjava2examples.module.rxjava2.operators.item;

import android.annotation.SuppressLint;
import android.util.Log;

import com.nanchen.rxjava2examples.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * map
 *    将一个 Observable 对象通过某种关系转换为另一个Observable 对象 (同1.x)
 *    2.x将1.x中的 Func1 和 Func2 改为 Function 和 BiFunction  
 */

public class RxMapActivity extends RxOperatorBaseActivity {
    private static final String TAG = "RxMapActivity";

    @Override
    protected String getSubTitle() {
        return getString(R.string.rx_map);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void doSomething() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                mRxOperatorsText.append("accept : " + s +"\n");
                Log.e(TAG, "accept : " + s +"\n" );
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void doOtherthing() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer integer) throws Exception {
                return integer % 2 == 0;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                mRxOperatorsText.append("accept : " + aBoolean +"\n");
            }
        });
    }
}
