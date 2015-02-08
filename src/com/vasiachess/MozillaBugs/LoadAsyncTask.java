package com.vasiachess.MozillaBugs;

import android.content.Context;
import android.os.AsyncTask;
import com.vasiachess.MozillaBugs.models.BugModel;
import com.vasiachess.MozillaBugs.models.CommentsModel;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Vasiliy on 04.02.2015.
 */
public class LoadAsyncTask extends AsyncTask<String, Void, BugModel> {

    private Context mContext;
    private OnDataLoadListener mOnDataLoadListener;

    public LoadAsyncTask(Context _context, OnDataLoadListener _onDataLoadListener) {
        mContext = _context;
        mOnDataLoadListener = _onDataLoadListener;
    }

    @Override
    protected BugModel doInBackground(String... strings) {
        BugModel model = null;
        try {
            model = loadData(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return model;
    }

    @Override
    protected void onPostExecute(BugModel model) {
        if (model != null) {
            mOnDataLoadListener.onSuccess(model);
        } else {
            mOnDataLoadListener.onFailure();
        }
        mContext = null;
    }

    private BugModel loadData(String _url) throws IOException, JSONException {

        BugModel bugModel = new BugModel();
        bugModel.comments = new ArrayList<CommentsModel>();

        String jsonString = getJsonString(_url);

        JSONObject rootObj = new JSONObject(jsonString);

        JSONObject jBugs = rootObj.getJSONObject("bugs");

        JSONObject jID = jBugs.getJSONObject("707428");


        JSONArray jComments = jID.getJSONArray("comments");
        for (int i = 0; i < jComments.length(); i++) {
            JSONObject jComment = jComments.getJSONObject(i);
            CommentsModel comment = parseComment(jComment);
            bugModel.comments.add(comment);
        }

        return bugModel;

    }

    private String getJsonString(String _url) throws IOException {
        
        URL url = new URL(_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestMethod(HttpGet.METHOD_NAME);
        
        final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }


    private CommentsModel parseComment(JSONObject _jComment) throws JSONException, IOException {
        CommentsModel comment = new CommentsModel();

        comment.attachment_id = _jComment.getString("attachment_id");
        comment.author = _jComment.getString("author");
        comment.bug_id = _jComment.getString("bug_id");
        comment.creation_time = _jComment.getString("creation_time");
        comment.creator = _jComment.getString("creator");
        comment.id = _jComment.getString("id");
        comment.is_private = _jComment.getString("is_private");
        comment.raw_text = _jComment.getString("raw_text");
        comment.tags = _jComment.getString("tags");
        comment.text = _jComment.getString("text");
        comment.time = _jComment.getString("time");

        return comment;
    }
}
