package vn.viettel.browser.listener;

import android.view.View;

import vn.viettel.browser.models.GesturesAction;


/**
 * Created by vinh on 7/28/15.
 */
public interface GesturesListener {
    public void onGesturesClick(View view);
    public void onGesturesAction(GesturesAction action);
}
