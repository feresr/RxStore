package com.feresr.rxstore.model;

/**
 * Created by feresr on 1/2/17.
 */

public class JokeResponse {

    public Joke getJoke() {
        return joke;
    }

    public void setJoke(Joke joke) {
        this.joke = joke;
    }

    private Joke joke;
    private boolean hasError = false;
    private String errorString;


    public void setError(String error) {
        hasError = true;
        errorString = error;
    }

    public boolean isSuccessful() {
        return !hasError;
    }

    public String getErrorString() {
        return errorString;
    }
}
