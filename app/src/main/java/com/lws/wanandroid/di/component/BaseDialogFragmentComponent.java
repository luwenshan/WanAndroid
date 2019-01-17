package com.lws.wanandroid.di.component;

import com.lws.wanandroid.base.fragment.BaseDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseDialogFragmentComponent extends AndroidInjector<BaseDialogFragment> {
    @Subcomponent.Builder
    abstract class BaseBuilder extends Builder<BaseDialogFragment> {

    }
}
