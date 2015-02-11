package com.example.TestAndroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ZOG on 1/8/2015.
 */
public class FActivity extends Activity implements View.OnClickListener {

    private Button btnFragment;
    private Button btnWhatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment);

        btnFragment = (Button) findViewById(R.id.btnFragment);
        btnWhatFragment = (Button) findViewById(R.id.btnWhatFragment);
        btnFragment.setOnClickListener(this);
        btnWhatFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnFragment) {
            onClickBtnFragment();
        } else if (view == btnWhatFragment) {
            onClickBtnWhatFragment();
        }
    }

    private void onClickBtnFragment() {
        //Must have empty constructor.
        MyFragment fragment = new MyFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rlForFragment, fragment);
//        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void onClickBtnWhatFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        MyFragment fragment = (MyFragment) fragmentManager.findFragmentById(R.id.rlForFragment);
//        MyFragment fragment = (MyFragment) fragmentManager.findFragmentByTag("tag");

        if (fragment == null) {
            Toast.makeText(this, "No fragment", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Fragment is: " + fragment.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
        fragment.update();
    }
}
