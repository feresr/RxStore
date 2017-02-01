package com.feresr.rxstore;

import com.feresr.rxstore.common.BaseView;

/**
 * Created by feresr on 1/2/17.
 */

public interface JokesView extends BaseView {
    public void displayJoke(String joke);
    public void displayError(String error);
}
