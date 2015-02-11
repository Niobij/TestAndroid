package com.example.TestAndroid;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by ZOG on 1/8/2015.
 */
public class MyFragment extends Fragment {

    private static final String TAG = "fragment";

    private static int msCount = 0;

    private TextView tvFragmentCount;
    private RelativeLayout rlRoot;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "MyFragment#onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MyFragment#onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "MyFragment#onCreateView");

        msCount++;

        View view = inflater.inflate(R.layout.fragment_my, container, false);

        tvFragmentCount = (TextView) view.findViewById(R.id.tvFragmentCount);
        rlRoot = (RelativeLayout) view.findViewById(R.id.rlRoot);

        Random random = new Random(msCount + (34234234 >> 43 * 43));
        rlRoot.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "MyFragment#onActivityCreated");

        tvFragmentCount.setText("MyFragment: " + msCount);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "MyFragment#onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "MyFragment#onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "MyFragment#onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "MyFragment#onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "MyFragment#onDestroyView");

        msCount--;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MyFragment#onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "MyFragment#onDetach");
    }

    public void update() {
        tvFragmentCount.setText(tvFragmentCount.getText() + "\nupdated");
    }

}
