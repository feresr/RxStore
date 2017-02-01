package com.feresr.rxstore.DI.module;

import android.content.Context;

import com.feresr.rxstore.RxStoreApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by feresr on 25/07/16.
 * <p>
 * The module due is create objects to solve dependencies
 * trough methods annotated with {@link Provides} annotation.
 */
@Module
public class AppModule {

    private final RxStoreApplication app;

    public AppModule(RxStoreApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public RxStoreApplication provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app.getApplicationContext();
    }
}
