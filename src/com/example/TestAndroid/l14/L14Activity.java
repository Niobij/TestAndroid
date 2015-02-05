package com.example.TestAndroid.l14;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.TestAndroid.R;

/**
 * Created by ZOG on 1/26/2015.
 */
public class L14Activity extends Activity implements View.OnClickListener {

    public static final String TAG = L14Activity.class.getSimpleName();
    public static final byte STEPS = 4;

    private TextView tvText_AL14;

    private Counter mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l14);

        findViewById(R.id.btnThread_AL14).setOnClickListener(this);
        tvText_AL14 = (TextView) findViewById(R.id.tvText_AL14);

        mCounter = new Counter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnThread_AL14:
                onClickAsyncTask();
                break;
        }
    }


    private void onClickBtnThread() {
//        MyRunnable myRunnable = new MyRunnable();
//        myRunnable.run();
//        new Thread(myRunnable).start();

        final MyThread myThread = new MyThread();
        myThread.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myThread.interrupt();
            }
        }, 2500);
    }

    private int count = 100000;
    private void onClickBtnThreadSynch() {
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "A: value: " + mCounter.value());

                for (int i = 0; i < count; i++) {
                    mCounter.increment();
                }
                Log.d(TAG, "A: increment: " + mCounter.value());

                for (int i = 0; i < count; i++) {
                    mCounter.decrement();
                }
                Log.d(TAG, "A: decrement: " + mCounter.value());
            }
        });

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "B: value: " + mCounter.value());

                for (int i = 0; i < count; i++) {
                    mCounter.decrement();
                }
                Log.d(TAG, "B: decrement: " + mCounter.value());

                for (int i = 0; i < count; i++) {
                    mCounter.increment();
                }
                Log.d(TAG, "B: increment: " + mCounter.value());
            }
        });

        A.start();
        B.start();
    }

    private void onClickThreadUi() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//				tvText_AL14.setText("Updated from thread");

//                Activity.runOnUiThread(Runnable)
//                View.post(Runnable)
//                View.postDelayed(Runnable, long)

//                tvText_AL14.post(new Runnable() {
//                    @Override
//                    public void run() {
//				        tvText_AL14.setText("Updated from thread");
//                    }
//                });

                L14Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvText_AL14.setText("Updated from thread");
                    }
                });
            }
        }).start();
    }

    private void onClickAsyncTask() {
        MyAsyncTask myAsyncTask = new MyAsyncTask(tvText_AL14);
//        myAsyncTask.execute("some data", "", "adsf", "sdfa");
        myAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "some data");
    }

}
