package com.lws.wanandroid.di.module;

import com.lws.wanandroid.di.component.BaseFragmentComponent;
import com.lws.wanandroid.ui.fragment.CollectFragment;
import com.lws.wanandroid.ui.fragment.KnowledgeHierarchyFragment;
import com.lws.wanandroid.ui.fragment.MainPagerFragment;
import com.lws.wanandroid.ui.fragment.NavigationFragment;
import com.lws.wanandroid.ui.fragment.ProjectFragment;
import com.lws.wanandroid.ui.fragment.SettingFragment;
import com.lws.wanandroid.ui.fragment.WxArticleFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseFragmentComponent.class})
public abstract class AbstractAllFragmentModule {
    @ContributesAndroidInjector(modules = MainPagerFragmentModule.class)
    abstract MainPagerFragment contributesMainPagerFragmentInjector();

    @ContributesAndroidInjector(modules = CollectFragmentModule.class)
    abstract CollectFragment contributesCollectFragmentInject();

    @ContributesAndroidInjector(modules = KnowledgeFragmentModule.class)
    abstract KnowledgeHierarchyFragment contributesKnowledgeHierarchyFragmentInject();

    @ContributesAndroidInjector(modules = NavigationFragmentModule.class)
    abstract NavigationFragment contributesNavigationFragmentInject();

    @ContributesAndroidInjector(modules = WxArticleFragmentModule.class)
    abstract WxArticleFragment contributesWxArticleFragmentInject();

    @ContributesAndroidInjector(modules = ProjectFragmentModule.class)
    abstract ProjectFragment contributesProjectFragmentInject();

    @ContributesAndroidInjector(modules = SettingFragmentModule.class)
    abstract SettingFragment contributesSettingFragmentInject();
}
