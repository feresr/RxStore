package com.feresr.rxstore.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.feresr.rxstore.DI.HasComponent;
import com.feresr.rxstore.DI.component.ActivityComponent;
import com.feresr.rxstore.DI.module.ActivityModule;
import com.feresr.rxstore.RxStoreApplication;

import butterknife.ButterKnife;

/**
 * Created by feresr on 25/07/16.
 * The activity only will execute operations that affect the UI. These operations are defined
 * by a view model and are triggered by its presenter.
 * <p>
 */
public abstract class BaseActivity extends AppCompatActivity implements HasComponent<ActivityComponent> {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private ActivityComponent activityComponent;

    @Override
    public ActivityComponent getComponent() {
        return activityComponent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        createActivityComponent();

        super.onCreate(savedInstanceState);

        setContentView(getLayout());
        injectViews();

    }


    /**
     * Setup the object graph and inject the dependencies needed on this activity.
     */
    private void createActivityComponent() {
        activityComponent = RxStoreApplication.getApp(this).getActivityComponent(new ActivityModule(this));
    }

    /**
     * Every object annotated with ButterKnife.bind() its gonna injected trough butterknife
     */
    private void injectViews() {
        ButterKnife.bind(this);
    }


    /**
     * @return The layout that's gonna be the activity view.
     */
    protected abstract int getLayout();

}
