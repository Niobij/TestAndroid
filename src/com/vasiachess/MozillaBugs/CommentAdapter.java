package com.vasiachess.MozillaBugs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.vasiachess.MozillaBugs.models.CommentsModel;

import java.util.ArrayList;

/**
 * Created by Vasiliy on 04.02.2015.
 */
public class CommentAdapter extends BaseAdapter {

    private ArrayList<CommentsModel> mComments;
    private Context mContext;

    public CommentAdapter(Context _context, ArrayList<CommentsModel> _comments) {
        mContext = _context;
        mComments = _comments;
    }


    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentsModel commentsModel = mComments.get(position);
        CommentView commentView = new CommentView(mContext);
        commentView.setCommentModel(commentsModel);
        return commentView;
    }
}
