package com.feresr.rxstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.feresr.rxstore.DI.component.ActivityComponent;
import com.feresr.rxstore.common.BaseFragment;

import butterknife.BindView;

/**
 * Created by feresr on 1/2/17.
 */

public class JokesFragment extends BaseFragment<JokesPresenter> implements JokesView {

    @BindView(R.id.jokeText)
    TextView jokeText;
    @BindView(R.id.jokeButton)
    Button jokeButton;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fetchNewJoke();
            }
        });
    }

    @Override
    public void displayJoke(String joke) {
        jokeText.setText(joke);
    }

    @Override
    public void displayError(String error) {
        jokeText.setText(error);
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void bindPresenterToView() {
        presenter.setView(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_jokes;
    }
}
