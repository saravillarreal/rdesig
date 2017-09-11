package rdesign.android.fragments;

import android.support.v4.app.Fragment;

import rdesign.android.activities.BaseActivity;

/**
 * Created by saravillarreal on 9/4/17.
 */

public abstract class BaseFragment extends Fragment  {
    /**
     * Mandatory
     * override this method to set all fragment view components events
     */
    abstract protected void setViewsEvents();


    /**
     * show the activity progress dialog
     *
     * @param message
     */
    public void showProgressDialog(String message) {
        ((BaseActivity) getActivity()).showDialog();
    }

    /**
     * dismiss the activity progress dialog
     */
    public void hideProgressDialog() {
        try {
            ((BaseActivity) getActivity()).dismissDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
