package com.feresr.rxstore.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.feresr.rxstore.DI.HasComponent;
import com.feresr.rxstore.DI.component.ActivityComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by feresr on 25/07/16.
 * <p>
 * A fragment like an activity only will execute operations that affect the UI.
 * These operations are defined by a view model and are triggered by its presenter.
 */
public abstract class BaseFragment<C extends BasePresenter> extends Fragment implements BaseView {

    @Inject
    protected C presenter;
    private Unbinder unbinder;

    public BaseFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(getActivityComponent());
        bindPresenterToView();
        presenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(hasSupportMenu());
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    protected boolean hasSupportMenu() {
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
    }

    protected abstract void injectDependencies(ActivityComponent activityComponent);

    protected abstract void bindPresenterToView();

    /**
     * Replace all the annotated fields with ButterKnife annotations with the proper value
     */
    private void bindViews(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Specify the layout of the fragment to be inflated in the {@link BaseFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    protected abstract int getFragmentLayout();

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        presenter = null;
        super.onDestroy();
    }

    public void keepScreenOn() {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    private ActivityComponent getActivityComponent() {
        try {
            return ((HasComponent<ActivityComponent>) getActivity()).getComponent();
        } catch (ClassCastException e) {
            Log.e(this.getClass().getSimpleName(), "The parent activity for this fragment must implement -HasComponent<ActivityComponent> interface", e);
        }
        return null;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
