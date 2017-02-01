package com.feresr.rxstore;

import android.os.Bundle;

import com.feresr.rxstore.common.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, new JokesFragment()).commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_base;
    }
}
