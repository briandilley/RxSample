package com.example.briandilley.rxsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity
        extends AppCompatActivity {

    private TextView text;
    private Button test1;
    private Button test2;
    private Button test3;
    private Button test4;
    private Button test5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);

        test1 = (Button) findViewById(R.id.test1);
        test2 = (Button) findViewById(R.id.test2);
        test3 = (Button) findViewById(R.id.test3);
        test4 = (Button) findViewById(R.id.test4);
        test5 = (Button) findViewById(R.id.test5);
    }

    @Override
    protected void onResume() {
        super.onResume();

        test1.setOnClickListener(__ -> {
            text.setText("");
            observe("test1")
                    .map((v) -> {
                        log("test1.map: " + v, Thread.currentThread());
                        return v;
                    })
                    .doOnNext((v) -> {
                        log("test1.doOnNext: " + v, Thread.currentThread());
                    })
                    .doOnSubscribe(() -> {
                        log("test1.doOnSubscribe:", Thread.currentThread());
                    })
                    .subscribe((v) -> {
                        log("test1.subscribe: " + v, Thread.currentThread());
                    });
        });

        test2.setOnClickListener(__ -> {
            text.setText("");
            observe("test2")
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .map((v) -> {
                        log("test2.map: " + v, Thread.currentThread());
                        return v;
                    })
                    .doOnNext((v) -> {
                        log("test2.doOnNext: " + v, Thread.currentThread());
                    })
                    .doOnSubscribe(() -> {
                        log("test2.doOnSubscribe:", Thread.currentThread());
                    })
                    .subscribe((v) -> {
                        log("test2.subscribe: " + v, Thread.currentThread());
                    });
        });

        test3.setOnClickListener(__ -> {
            text.setText("");
            observe("test3")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .map((v) -> {
                        log("test3.map: " + v, Thread.currentThread());
                        return v;
                    })
                    .doOnNext((v) -> {
                        log("test3.doOnNext: " + v, Thread.currentThread());
                    })
                    .doOnSubscribe(() -> {
                        log("test3.doOnSubscribe:", Thread.currentThread());
                    })
                    .subscribe((v) -> {
                        log("test3.subscribe: " + v, Thread.currentThread());
                    });
        });

        test4.setOnClickListener(__ -> {
            text.setText("");
            observe("test4")
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .map((v) -> {
                        log("test4.map: " + v, Thread.currentThread());
                        return v;
                    })
                    .doOnNext((v) -> {
                        log("test4.doOnNext: " + v, Thread.currentThread());
                    })
                    .doOnSubscribe(() -> {
                        log("test4.doOnSubscribe:", Thread.currentThread());
                    })
                    .subscribe((v) -> {
                        log("test4.subscribe: " + v, Thread.currentThread());
                    });
        });

        test5.setOnClickListener(__ -> {
            text.setText("");
            observe("test5")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .map((v) -> {
                        log("test5.map: " + v, Thread.currentThread());
                        return v;
                    })
                    .doOnNext((v) -> {
                        log("test5.doOnNext: " + v, Thread.currentThread());
                    })
                    .doOnSubscribe(() -> {
                        log("test5.doOnSubscribe:", Thread.currentThread());
                    })
                    .subscribe((v) -> {
                        log("test5.subscribe: " + v, Thread.currentThread());
                    });
        });


    }

    private Observable<String> observe(String text) {
        return Observable.create((subscriber) -> {
            log(text+".onSubscribe", Thread.currentThread());
            subscriber.onNext(text);
            subscriber.onCompleted();
        });
    }

    private void log(String message, Thread thread) {
        text.post(() -> {
            String value = text.getText().toString();
            value += "\n[" + thread.getName() + "] " + message;
            text.setText(value);
        });
    }
}
