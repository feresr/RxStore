package com.feresr.rxstore.model;

/**
 * Created by feresr on 1/2/17.
 */

public class JokeRequest {

    public boolean shouldFetchNewOne() {
        return fetchANewOne;
    }

    public boolean fetchANewOne = false;

    public JokeRequest(boolean newOne) {
        super();
        this.fetchANewOne = newOne;
    }
}
