package com.feresr.rxstore.common;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by feresr on 25/07/16.
 * Defines common functionality for a BaseView (fragment or activity)
 */
public interface BaseView {
    FragmentActivity getActivity();

    BaseActivity getBaseActivity();

    Bundle getArguments();

    void keepScreenOn();

    void requestPermissions(String[] permissions, int requestCode);
}
