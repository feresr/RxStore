package com.feresr.rxstore.DI.module;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.feresr.rxstore.DI.ActivityScope;
import com.feresr.rxstore.common.BaseActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by feresr on 26/07/16.
 */
@Module
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @ActivityScope
    BaseActivity provideFragmentActivity() {
        return this.activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @ActivityScope
    Activity provideActivity() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    public FragmentManager getFragmentManager(FragmentActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
