package com.vasiachess.MozillaBugs;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.vasiachess.MozillaBugs.models.CommentsModel;

/**
 * Created by Vasiliy on 04.02.2015.
 */
public class CommentView extends RelativeLayout {

    private TextView tvCommentData;

    public CommentView(Context context) {
        super(context);

        inflate(context, R.layout.view_comment, this);

        tvCommentData = (TextView) findViewById(R.id.tvCommentData_VC);
    }

    public void setCommentModel(CommentsModel _model) {

        tvCommentData.setText(

        "attachment_id: " + _model.attachment_id + '\n' +
        "author: " + _model.author + '\n' +
        "bug_id: " + _model.bug_id + '\n' +
        "creation_time: " + _model.creation_time + '\n' +
        "creator: " + _model.creator + '\n' +
        "id: " + _model.id + '\n' +
        "is_private: " + _model.is_private + '\n' +
        "raw_text: " + _model.raw_text + '\n' +
        "tags: " + _model.tags + '\n' +
        "text: " + _model.text + '\n' +
        "time: " + _model.time
        );
    }
}
