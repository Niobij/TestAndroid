package com.example.TestAndroid.l19;

import java.io.Serializable;

/**
 * Created by Vasiliy on 15.02.2015.
 */
public class NotificationModel implements Serializable {

    public String message;
    public String title;
    public String subtitle;
    public String tickerText;
    public int vibrate;
    public int sound;
}
