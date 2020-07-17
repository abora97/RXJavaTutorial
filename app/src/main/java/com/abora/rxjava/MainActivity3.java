package com.abora.rxjava;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.abora.rxjava.databinding.ActivityMain3Binding;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

public class MainActivity3 extends AppCompatActivity {
    ActivityMain3Binding binding;
    private static final String TAG = "MainActivity3";


    /*
        map: upStream ---> process ----> downStream

     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main3);
        binding.setLifecycleOwner(this);

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                binding.EdName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() != 0)
                            emitter.onNext(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        })
                .doOnNext(c -> Log.d(TAG, "onCreate: upStream " + c))
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Throwable {
                        return Integer.parseInt(o.toString()) * 2;
                    }
                })
                // for delay request using with google map places or when i need not spam API
                .debounce(2, TimeUnit.SECONDS)
                // for hit API when input data is changed
                .distinctUntilChanged()
                .filter(c -> !c.toString().equals("2"))
                .flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Throwable {
                        return sendDataToAPI(o.toString());
                    }
                })
                .subscribe(s -> {
                    Log.d(TAG, "onCreate: downStream " + s);
                 //   sendDataToAPI(s.toString());
                });


    }

    public Observable sendDataToAPI(String data) {
        Observable observable = Observable.just("Calling api 1 to send " + data);

        observable.subscribe(c -> Log.d(TAG, "sendDataToAPI: " + c));

        return observable;
    }
}