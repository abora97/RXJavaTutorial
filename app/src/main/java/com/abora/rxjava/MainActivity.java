package com.abora.rxjava;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      coldObservable();

    }

    private void coldObservable() {
        Observable<Long> cold=Observable.intervalRange(0,5,0,1, TimeUnit.SECONDS);

        cold.subscribe(i-> Log.d(TAG,"Observable cold output 1: "+i));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cold.subscribe(i-> Log.d(TAG,"Observable cold output 2: "+i));
    }
}