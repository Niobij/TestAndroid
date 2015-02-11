package com.example.TestAndroid.l14;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ZOG on 1/26/2015.
 */
public class Counter {
    private volatile int c = 0;
    private int c2 = 0;

    private Object lock = new Object();
    private Object lock2 = new Object();


    public void increment() {
        synchronized (this) {
            c++;
            decrement();
        }
        synchronized (lock2) {
            c2++;
        }

//        cli
//                mov ax, 5
//        sli
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}
