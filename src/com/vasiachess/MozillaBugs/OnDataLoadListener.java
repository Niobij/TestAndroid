package com.vasiachess.MozillaBugs;

import com.vasiachess.MozillaBugs.models.BugModel;

/**
 * Created by Vasiliy on 04.02.2015.
 */
public interface OnDataLoadListener {

    public void onSuccess(BugModel _model);
    public void onFailure();

}
