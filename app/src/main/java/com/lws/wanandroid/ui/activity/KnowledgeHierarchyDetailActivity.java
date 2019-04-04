package com.lws.wanandroid.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.activity.BaseMVPActivity;
import com.lws.wanandroid.base.fragment.BaseMVPFragment;
import com.lws.wanandroid.contract.KnowledgeHierarchyDetailContract;
import com.lws.wanandroid.model.bean.KnowledgeHierarchyData;
import com.lws.wanandroid.presenter.KnowledgeHierarchyDetailPresenter;
import com.lws.wanandroid.ui.fragment.KnowledgeHierarchyListFragment;
import com.lws.wanandroid.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KnowledgeHierarchyDetailActivity extends BaseMVPActivity<KnowledgeHierarchyDetailPresenter>
        implements KnowledgeHierarchyDetailContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.knowledge_hierarchy_detail_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.knowledge_hierarchy_detail_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.knowledge_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private List<KnowledgeHierarchyData> knowledgeHierarchyDataList;
    private List<BaseMVPFragment> mFragments = new ArrayList<>();
    private String chapterName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_hierarchy_detail;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.main_status_bar_blue), 1f);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        if (getIntent().getBooleanExtra(Constants.IS_SINGLE_CHAPTER, false)) {
            startSingleChapterPager();
        } else {
            startNormalKnowledgeListPager();
        }
    }

    @Override
    protected void initEventAndData() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null ? 0 : mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (getIntent().getBooleanExtra(Constants.IS_SINGLE_CHAPTER, false)) {
                    return chapterName;
                } else {
                    return knowledgeHierarchyDataList.get(position).getName();
                }
            }
        });
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void showSwitchProject() {
        onBackPressedSupport();
    }

    @Override
    public void showSwitchNavigation() {
        onBackPressedSupport();
    }

    /**
     * 装载多个列表的知识体系页面（knowledge进入）
     */
    private void startNormalKnowledgeListPager() {
        KnowledgeHierarchyData knowledgeHierarchyData = (KnowledgeHierarchyData) getIntent().getSerializableExtra(Constants.ARG_PARAM1);
        if (knowledgeHierarchyData == null || knowledgeHierarchyData.getName() == null) {
            return;
        }
        mTitleTv.setText(knowledgeHierarchyData.getName().trim());
        knowledgeHierarchyDataList = knowledgeHierarchyData.getChildren();
        if (knowledgeHierarchyDataList == null) {
            return;
        }
        for (KnowledgeHierarchyData data : knowledgeHierarchyDataList) {
            mFragments.add(KnowledgeHierarchyListFragment.newInstance(data.getId()));
        }
    }

    /**
     * 装载单个列表的知识体系页面（tag进入）
     */
    private void startSingleChapterPager() {
        String superChapterName = getIntent().getStringExtra(Constants.SUPER_CHAPTER_NAME);
        chapterName = getIntent().getStringExtra(Constants.CHAPTER_NAME);
        int chapterId = getIntent().getIntExtra(Constants.CHAPTER_ID, 0);
        mTitleTv.setText(superChapterName);
        mFragments.add(KnowledgeHierarchyListFragment.newInstance(chapterId));
    }
}
