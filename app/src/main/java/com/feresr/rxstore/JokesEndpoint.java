package com.feresr.rxstore;
import com.feresr.rxstore.model.Joke;
import com.feresr.rxstore.model.JokeResponse;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by feresr on 26/07/16.
 */
public interface JokesEndpoint {

    @GET("jokes/random")
    Observable<Response<Joke>> getRandomJoke();

}
