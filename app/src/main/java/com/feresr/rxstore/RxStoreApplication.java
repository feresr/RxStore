package com.feresr.rxstore;

import android.app.Application;
import android.content.Context;

import com.feresr.rxstore.DI.component.ActivityComponent;
import com.feresr.rxstore.DI.component.ApplicationComponent;
import com.feresr.rxstore.DI.component.DaggerApplicationComponent;
import com.feresr.rxstore.DI.module.ActivityModule;
import com.feresr.rxstore.DI.module.AppModule;

/**
 * Created by feresr on 25/07/16.
 */
public class RxStoreApplication extends Application {

    private ApplicationComponent component;

    public static RxStoreApplication getApp(Context context) {
        return (RxStoreApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupGraph();
    }

    private void setupGraph() {
        component = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this)).build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public ActivityComponent getActivityComponent(ActivityModule module) {
        return component.plus(module);
    }
}
