package com.feresr.rxstore.DI.component;


import com.feresr.rxstore.DI.ActivityScope;
import com.feresr.rxstore.DI.module.ActivityModule;
import com.feresr.rxstore.JokesFragment;
import com.feresr.rxstore.MainActivity;

import dagger.Subcomponent;

/**
 * Created by feresr on 25/07/16.
 */
@ActivityScope
@Subcomponent(
        modules = {ActivityModule.class}
)
public interface ActivityComponent {

    void inject(JokesFragment jokesFragment);
}
