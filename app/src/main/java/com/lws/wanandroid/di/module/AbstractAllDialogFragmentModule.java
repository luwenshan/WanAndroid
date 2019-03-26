package com.lws.wanandroid.di.module;

import com.lws.wanandroid.di.component.BaseDialogFragmentComponent;
import com.lws.wanandroid.ui.fragment.UsageDialogFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseDialogFragmentComponent.class})
public abstract class AbstractAllDialogFragmentModule {
    @ContributesAndroidInjector(modules = UsageDialogFragmentModule.class)
    abstract UsageDialogFragment contributesUsageDialogFragmentInjector();
}
