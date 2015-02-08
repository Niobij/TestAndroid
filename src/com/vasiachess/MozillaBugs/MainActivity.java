package com.vasiachess.MozillaBugs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.vasiachess.MozillaBugs.models.BugModel;
import com.vasiachess.MozillaBugs.models.CommentsModel;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener, OnDataLoadListener {

    private Button btnLoad;
    private ProgressBar pbLoading;
    private TextView tvBug;
    private ListView lvComments;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViews();
        btnLoad.setOnClickListener(this);
    }


    private void findViews() {
        btnLoad = (Button) findViewById(R.id.btnLoad);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        tvBug = (TextView) findViewById(R.id.tvBug);
        lvComments = (ListView) findViewById(R.id.lvComments);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLoad) {
            pbLoading.setVisibility(View.VISIBLE);
            new LoadAsyncTask(this, this).execute("https://bugzilla.mozilla.org/rest/bug/707428/comment");
        }
    }



    private void setCommentsModel(BugModel _model) {

        tvBug.setText(
                "Bug - 707428" + '\n' + "  comments:"
        );

        ArrayList<CommentsModel> comments = _model.comments;
        CommentAdapter commentAdapter = new CommentAdapter(this, comments);
        lvComments.setAdapter(commentAdapter);
    }

    @Override
    public void onSuccess(BugModel _model) {
        pbLoading.setVisibility(View.GONE);
        setCommentsModel(_model);
    }

    @Override
    public void onFailure() {
        pbLoading.setVisibility(View.GONE);
        Toast.makeText(this, "Failed load data", Toast.LENGTH_SHORT).show();
    }
}

