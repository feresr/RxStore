package com.feresr.rxstore.DI.component;

import com.feresr.rxstore.DI.module.ActivityModule;
import com.feresr.rxstore.DI.module.AppModule;
import com.feresr.rxstore.DI.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by feresr on 25/07/16.
 * The component due is provide methods that the object graph can use to inject dependencies.
 * Its like an API for our object graph. <br>
 * <p>
 * Those methods inject objects created on corresponding modules.
 */

@Singleton
@Component(
        modules = {AppModule.class, NetworkModule.class}
)
public interface ApplicationComponent {

    ActivityComponent plus(ActivityModule activityModule);

}