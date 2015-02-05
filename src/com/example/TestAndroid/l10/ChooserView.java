package com.example.TestAndroid.l10;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.TestAndroid.R;

/**
 * Created by ZOG on 1/12/2015.
 */
public class ChooserView extends LinearLayout implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rgMain;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private TextView tvSelect;

    public ChooserView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.chooser_view, this);

        rgMain = (RadioGroup) findViewById(R.id.rgMain);
        rb1 = (RadioButton) findViewById(R.id.rbOne);
        rb2 = (RadioButton) findViewById(R.id.rbTwo);
        rb3 = (RadioButton) findViewById(R.id.rbThree);
        tvSelect = (TextView) findViewById(R.id.tvSelect);

        rgMain.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int _checkedId) {
        switch (_checkedId) {
            case R.id.rbOne:
                checked(_checkedId);
                break;

            case R.id.rbTwo:
                checked(_checkedId);
                break;

            case R.id.rbThree:
                checked(_checkedId);
                break;
        }
    }

    private void checked(int checkedId) {
        tvSelect.setText("Checked id: " + checkedId);
    }

    public int getCheckedRbId() {
        return rgMain.getCheckedRadioButtonId();
    }
}
