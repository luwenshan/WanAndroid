package com.lws.wanandroid.di.component;

import com.lws.wanandroid.base.fragment.BaseMVPDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseDialogFragmentComponent extends AndroidInjector<BaseMVPDialogFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseMVPDialogFragment> {
    }
}
