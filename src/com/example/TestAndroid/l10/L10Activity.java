package com.example.TestAndroid.l10;

import android.animation.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
import android.widget.TextView;
import android.widget.Toast;
import com.example.TestAndroid.R;
import org.w3c.dom.Text;

/**
 * Created by ZOG on 1/12/2015.
 */
public class L10Activity extends Activity implements View.OnClickListener, View.OnTouchListener {

    private TextView tvAnimate_l10A;
    private ChooserView customView;
    private TextView tvTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l10);

        tvAnimate_l10A = (TextView) findViewById(R.id.tvAnimate_l10A);
        tvAnimate_l10A.setOnClickListener(this);

        customView = (ChooserView) findViewById(R.id.customView);

        tvTouch = (TextView) findViewById(R.id.tvTouch);
        tvTouch.setOnTouchListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == tvAnimate_l10A) {
//            doAnimatorSet(tvAnimate_l10A);
            Toast.makeText(this, "Checked: " + customView.getCheckedRbId(), Toast.LENGTH_SHORT).show();
        }
    }

    private void doAnimation1(View _view) {
        _view.animate().y(200).scaleY(2f);

        TranslateAnimation animation = new TranslateAnimation(0, 100, 0, 300);
        animation.setDuration(600);
        _view.startAnimation(animation);
    }


    private void doAnimation2(View _view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_tween);
        _view.startAnimation(animation);
    }

    private void doAnimationSet(View _view) {
        Animation animationSet = AnimationUtils.loadAnimation(this, R.anim.anim_set);
        _view.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(L10Activity.this, "Animation finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        _view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(L10Activity.this, "2000", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    private void doAnimator1(View _view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(_view, "x", 0f, 300f);
        animator.setDuration(800);
//        animator.setInterpolator(new LinearInterpolator());
        animator.setInterpolator(new OvershootInterpolator());
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {Toast.makeText(L10Activity.this, "Animation finished", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doAnimator2(View _view) {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.animator1);
        animator.setTarget(_view);
        animator.start();
    }

    private void doAnimatorSet(View _view) {
        int duration = 1000;

        ObjectAnimator animX = ObjectAnimator.ofFloat(_view, View.X, 0f, 50f, 0f);
        animX.setDuration(duration);

        ObjectAnimator animY = ObjectAnimator.ofFloat(_view, "y", 0f, 100f, 0f);
        animY.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animX, animY);
        animatorSet.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == MotionEvent.ACTION_UP) {
            Toast.makeText(this, "UP", Toast.LENGTH_SHORT).show();
        }

        ((TextView) view).setText(motionEvent.getActionMasked() + "");

        return true;
    }
}
