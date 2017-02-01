package com.feresr.rxstore.model;

/**
 * Created by feresr on 1/2/17.
 */

public class JokeRequest {

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }

    private boolean consumed = false;

    public boolean wasConsumed() {
        return consumed;
    }
}
