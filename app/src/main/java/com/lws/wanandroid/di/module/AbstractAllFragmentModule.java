package com.lws.wanandroid.di.module;

import com.lws.wanandroid.di.component.BaseFragmentComponent;
import com.lws.wanandroid.ui.fragment.MainPagerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseFragmentComponent.class})
public abstract class AbstractAllFragmentModule {
    @ContributesAndroidInjector(modules = MainPagerFragmentModule.class)
    abstract MainPagerFragment contributesMainPagerFragmentInjector();
}
