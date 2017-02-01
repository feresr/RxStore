package com.feresr.rxstore.DI.module;

import com.feresr.rxstore.BuildConfig;
import com.feresr.rxstore.JokesEndpoint;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by feresr on 26/07/16.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public JokesEndpoint provideEndpoints() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://api.icndb.com")
                .build();

        return retrofit.create(JokesEndpoint.class);
    }
}
