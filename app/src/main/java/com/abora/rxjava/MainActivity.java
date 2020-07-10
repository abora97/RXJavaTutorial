package com.abora.rxjava;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  coldObservable();
        // hotConnectableColdObservable();

        //subject
        //hotObservable();
        // hotBehaviorObservable();
        //hotReplayObservable();
        hotAsyncObservable();
    }

    // 6
    private void hotAsyncObservable() {

        //BehaviorSubject
        /*
            Doc A B C D  A B C D E F G complete
            p1                         G
            p2                         G
         */

        AsyncSubject<String> subject = AsyncSubject.create();
        subject.subscribe(i -> Log.d(TAG, "Observable cold output 1: " + i));
        subject.onNext("A");
        sleep(1000);
        subject.onNext("B");
        sleep(1000);
        subject.onNext("C");
        sleep(1000);
        subject.onNext("D");
        sleep(1000);
        subject.subscribe(i -> Log.d(TAG, "Observable cold output 2: " + i));
        subject.onNext("E");
        sleep(1000);
        subject.onNext("F");
        sleep(1000);
        subject.onNext("G");
        sleep(1000);
        subject.onComplete();

    }

    // 5
    private void hotReplayObservable() {

        //BehaviorSubject
        /*
            Doc A B C D  A B C D E F G
            p1  A B C D          E F G
            p2           A B C D E F G
         */

        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(i -> Log.d(TAG, "Observable cold output 1: " + i));
        subject.onNext("A");
        sleep(1000);
        subject.onNext("B");
        sleep(1000);
        subject.onNext("C");
        sleep(1000);
        subject.onNext("D");
        sleep(1000);
        subject.subscribe(i -> Log.d(TAG, "Observable cold output 2: " + i));
        subject.onNext("E");
        sleep(1000);
        subject.onNext("F");
        sleep(1000);
        subject.onNext("G");
        sleep(1000);


    }

    // 4
    private void hotBehaviorObservable() {

        //BehaviorSubject
        /*
            Doc A B C D  E F G
            p1  A B C D  E F G
            p2           D E F G
         */

        BehaviorSubject<String> subject = BehaviorSubject.create();
        subject.subscribe(i -> Log.d(TAG, "Observable cold output 1: " + i));
        subject.onNext("A");
        sleep(1000);
        subject.onNext("B");
        sleep(1000);
        subject.onNext("C");
        sleep(1000);
        subject.onNext("D");
        sleep(1000);
        subject.subscribe(i -> Log.d(TAG, "Observable cold output 2: " + i));
        subject.onNext("E");
        sleep(1000);
        subject.onNext("F");
        sleep(1000);
        subject.onNext("G");
        sleep(1000);


    }

    // 3
    private void hotObservable() {

        //publishSubject
        /*
            Doc A B C D  E F G
            p1  A B C D  E F G
            p2           E F G
         */

        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(i -> Log.d(TAG, "Observable cold output 1: " + i));
        subject.onNext("A");
        sleep(1000);
        subject.onNext("B");
        sleep(1000);
        subject.onNext("C");
        sleep(1000);
        subject.onNext("D");
        sleep(1000);
        subject.subscribe(i -> Log.d(TAG, "Observable cold output 2: " + i));
        subject.onNext("E");
        sleep(1000);
        subject.onNext("F");
        sleep(1000);
        subject.onNext("G");
        sleep(1000);


    }


    // 2
    private void hotConnectableColdObservable() {
        ConnectableObservable<Long> hot = ConnectableObservable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS).publish();
        hot.connect();
        hot.subscribe(i -> Log.d(TAG, "Observable cold output 1: " + i));
        sleep(3000);
        hot.subscribe(i -> Log.d(TAG, "Observable cold output 2: " + i));
    }

    // 1
    private void coldObservable() {
        Observable<Long> cold = Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS);

        cold.subscribe(i -> Log.d(TAG, "Observable cold output 1: " + i));
        sleep(3000);
        cold.subscribe(i -> Log.d(TAG, "Observable cold output 2: " + i));
    }

    public void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}