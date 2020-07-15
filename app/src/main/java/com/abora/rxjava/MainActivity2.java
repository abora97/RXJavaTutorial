package com.abora.rxjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


    /*
           type of Schedulers
                    Schedulers.io() retrofit database
                    Schedulers.computation() image resize video editing
                    Schedulers.newThread() new thread <don't use>
                    AndroidSchedulers.mainThread() ui thread
                    Schedulers.trampoline() FIFO
     */

/*
        upStream observable

        downStream observer

 */
public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .doOnNext(c -> Log.d(TAG, "onCreate: upStream " + c + " current thread " + Thread.currentThread().getName())) // everytime observable push or up thing
                .observeOn(Schedulers.computation())
                .subscribe(o -> Log.d(TAG, "onCreate: downStream " + o + " current thread " + Thread.currentThread().getName()));


        sleep(3000);

    }


    public void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}